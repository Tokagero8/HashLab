package hashlab.tests;

import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.benchmark.Benchmark;
import hashlab.benchmark.HashAlgorithmPerformanceTest;
import hashlab.core.HashAlgorithmFactory;
import hashlab.utils.DataGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestTask extends Task<Void> {

    private final String resultFileName;
    private final List<HashTestConfig> selectedTests;
    private StringProperty currentStatus = new SimpleStringProperty("");
    private String currentTestName = "";

    public TestTask(String resultFileName,  List<HashTestConfig> selectedTests) {
        this.resultFileName = resultFileName;
        this.selectedTests = selectedTests;
    }

    public void updateStatus(String additionalInfo){
        String status = "Running test: " + currentTestName + "\n" + additionalInfo;
        updateMessage(status);
    }

    public StringProperty getCurrentStatusProperty(){
        return currentStatus;
    }

    @Override
    protected Void call() throws Exception {

        String resultFilePath = resultFileName + ".csv";
        File resultFile = new File(resultFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile, true))){
            if (!resultFile.exists() || resultFile.length() == 0) {
                writer.write("Algorithm,Function,Table Size,Data Type,Data Size,Chunk Size,Operation,Result\n");
            }

            int totalTests = selectedTests.size();
            int currentTestIndex = 0;

            for(HashTestConfig testConfig : selectedTests) {
                if (isCancelled()) {
                    break;
                }

                currentTestName = testConfig.getTestName();
                updateStatus("Reading or generating data...");

                List<Map.Entry<String, String[]>> testKeysSets = getOrGenerateTestKeys(testConfig);

                int totalKeysSetsSize = testKeysSets.stream().mapToInt(entry -> entry.getValue().length).sum();
                Integer[] testValues = new Integer[totalKeysSetsSize];
                Arrays.fill(testValues, 1);

                updateStatus("Performing warmup...");

                List<HashAlgorithm<String, Integer>> WarmupAlgorithms = createHashAlgorithms(testConfig);
                performWarmup(testConfig, WarmupAlgorithms, testKeysSets, testValues);

                updateStatus("Executing algorithm...");

                double baseline = Benchmark.calculateBaseline(testConfig.getBenchmarkIterations(), testConfig.getBenchmarkThreshold());

                List<HashAlgorithm<String, Integer>> algorithms = createHashAlgorithms(testConfig);

                for (HashAlgorithm<String, Integer> algorithm : algorithms) {
                    for (Map.Entry<String, String[]> entry : testKeysSets) {
                        performAndWriteTest("put", testConfig, algorithm, entry, testValues, baseline, writer);
                        performAndWriteTest("get", testConfig, algorithm, entry, testValues, baseline, writer);
                        performAndWriteTest("delete", testConfig, algorithm, entry, testValues, baseline, writer);
                    }
                }

                currentTestIndex++;
                updateProgress(currentTestIndex, totalTests);
            }
        } catch (IOException e) {
            updateMessage("Error during saving a results: " + e.getMessage());
        }
        return null;
    }

    private List<HashAlgorithm<String, Integer>> createHashAlgorithms(HashTestConfig testConfig) {
        List<HashAlgorithm<String, Integer>> algorithms = new ArrayList<>();
        for (String hashFunction : testConfig.getHashFunctions()) {
            HashAlgorithm<String, Integer> algorithm = HashAlgorithmFactory.createAlgorithm(testConfig.getAlgorithm(), hashFunction, testConfig.getHashTableSize());
            algorithms.add(algorithm);
        }
        return algorithms;
    }

    private List<Map.Entry<String, String[]>> getOrGenerateTestKeys(HashTestConfig testConfig) {
        if (testConfig.isDataGenerated()) {
            return testConfig.isGeneratedOnAdd() ? getTestKeys(testConfig) : generateTestKeys(testConfig);
        } else {
            return testConfig.isLoadedOnAdd() ? prepareLoadedData(testConfig) : loadDataFromFile(testConfig);
        }
    }

    private List<Map.Entry<String, String[]>> getTestKeys(HashTestConfig testConfig){
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();
        if (testConfig.isUniformSelected()) {
            String[] keys = divideIntoChunks(testConfig.getUniformDataString(), testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Uniform", keys));
        }
        if (testConfig.isGaussianSelected()) {
            String[] keys = divideIntoChunks(testConfig.getGaussianDataString(), testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Gaussian", keys));
        }
        if (testConfig.isExponentialSelected()) {
            String[] keys = divideIntoChunks(testConfig.getExponentialDataString(), testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Exponential", keys));
        }
        return testKeysSets;
    }

    private List<Map.Entry<String, String[]>> generateTestKeys(HashTestConfig testConfig) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();

        if (testConfig.isUniformSelected()) {
            String[] keys = divideIntoChunks(DataGenerator.generateUniformASCIIValue(testConfig.getDataSize()), testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Uniform", keys));
        }
        if (testConfig.isGaussianSelected()) {
            String[] keys = divideIntoChunks(
                    DataGenerator.generateGaussianASCIIValue(
                            testConfig.getMean(),
                            testConfig.getDeviation(),
                            testConfig.getDataSize()),
                    testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Gaussian", keys));
        }
        if (testConfig.isExponentialSelected()) {
            String[] keys = divideIntoChunks(
                    DataGenerator.generateExponentialASCIIValue(
                            testConfig.getLambda(),
                            testConfig.getDataSize()),
                    testConfig.getChunkSize());
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Exponential", keys));
        }
        return testKeysSets;
    }

    private List<Map.Entry<String, String[]>> prepareLoadedData(HashTestConfig testConfig){
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();
        String[] keys = divideIntoChunks(testConfig.getLoadedDataString(), testConfig.getChunkSize());
        testKeysSets.add(new AbstractMap.SimpleEntry<>("FromFile", keys));
        return testKeysSets;
    }

    private List<Map.Entry<String, String[]>> loadDataFromFile(HashTestConfig testConfig) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();
        StringBuilder contentBuilder = new StringBuilder();
        File file = new File(testConfig.getSelectedFilePath());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            updateMessage("The file was not found: " + e.getMessage());
            return Collections.emptyList();
        } catch (IOException e) {
            updateMessage("Error during reading of the file: " + e.getMessage());
            return Collections.emptyList();
        }
        String[] keys = divideIntoChunks(contentBuilder.toString(), testConfig.getChunkSize());
        testKeysSets.add(new AbstractMap.SimpleEntry<>("FromFile", keys));
        return testKeysSets;
    }

    private String[] divideIntoChunks(String data, int chunkSize) {
        int numberOfChunks = (int)Math.ceil((double)data.length() / chunkSize);
        String[] dataChunks = new String[numberOfChunks];
        for(int i = 0, j = 0; i < numberOfChunks; i++, j += chunkSize) {
            dataChunks[i] = data.substring(j, Math.min(data.length(), j + chunkSize));
        }
        return dataChunks;
    }

    interface OperationStrategy {
        double execute(HashAlgorithm<String, Integer> algorithm, String[] data, Integer[] testValues, double baseline);
    }

    class PutStrategy implements OperationStrategy {
        @Override
        public double execute(HashAlgorithm<String, Integer> algorithm, String[] data, Integer[] testValues, double baseline) {
            algorithm.reset();
            HashAlgorithmPerformanceTest<String, Integer> performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, data, testValues);
            return performanceTest.runTest("PUT", baseline);
        }
    }

    class GetStrategy implements OperationStrategy {
        @Override
        public double execute(HashAlgorithm<String, Integer> algorithm, String[] data, Integer[] testValues, double baseline) {
            algorithm.reset();
            HashAlgorithmPerformanceTest<String, Integer> performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, data, testValues);
            performanceTest.runTest("PUT", baseline);
            return performanceTest.runTest("GET", baseline);
        }
    }

    class DeleteStrategy implements OperationStrategy {
        @Override
        public double execute(HashAlgorithm<String, Integer> algorithm, String[] data, Integer[] testValues, double baseline) {
            algorithm.reset();
            HashAlgorithmPerformanceTest<String, Integer> performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, data, testValues);
            performanceTest.runTest("PUT", baseline);
            return performanceTest.runTest("DELETE", baseline);
        }
    }

    private void performWarmup(HashTestConfig testConfig, List<HashAlgorithm<String, Integer>> algorithms, List<Map.Entry<String, String[]>> testKeysSets, Integer[] testValues){
        double baseline = Benchmark.calculateBaseline(testConfig.getBenchmarkIterations(), testConfig.getBenchmarkThreshold());
        for (HashAlgorithm<String, Integer> algorithm : algorithms) {
            for (Map.Entry<String, String[]> entry : testKeysSets) {
                performTest("put", testConfig, algorithm, entry, testValues, baseline);
                performTest("get", testConfig, algorithm, entry, testValues, baseline);
                performTest("delete", testConfig, algorithm, entry, testValues, baseline);
            }
        }
    }

    private void performTest(String operation, HashTestConfig testConfig, HashAlgorithm<String, Integer> algorithm, Map.Entry<String, String[]> entry, Integer[] testValues, double baseline) {
        if (operation.equals("put") && testConfig.isPutSelected() || operation.equals("get") && testConfig.isGetSelected() || operation.equals("delete") && testConfig.isDeleteSelected()) {
            OperationStrategy strategy = null;
            switch (operation.toLowerCase()) {
                case "put":
                    strategy = new PutStrategy();
                    break;
                case "get":
                    strategy = new GetStrategy();
                    break;
                case "delete":
                    strategy = new DeleteStrategy();
                    break;
            }

            for(int i = 0; i < testConfig.getWarmupIterations(); i++){
                updateStatus("Performing warmup..." +
                        "\nHash function: " +  algorithm.getHashFunction().getClass().getSimpleName() +
                        "\nData set: " + entry.getKey() +
                        "\nOperation: " + operation +
                        "\nIteration: " + i);
                strategy.execute(algorithm, entry.getValue(), testValues, baseline);
            }
        }
    }


    private void performAndWriteTest(String operation, HashTestConfig testConfig, HashAlgorithm<String, Integer> algorithm, Map.Entry<String, String[]> entry, Integer[] testValues, double baseline, BufferedWriter writer) throws IOException {
        if (operation.equals("put") && testConfig.isPutSelected() || operation.equals("get") && testConfig.isGetSelected() || operation.equals("delete") && testConfig.isDeleteSelected()) {

            double previousAverage = 0;
            double totalResult = 0;
            int iterations = 0;
            boolean thresholdMet = false;

            OperationStrategy strategy = null;
            switch (operation.toLowerCase()) {
                case "put":
                    strategy = new PutStrategy();
                    break;
                case "get":
                    strategy = new GetStrategy();
                    break;
                case "delete":
                    strategy = new DeleteStrategy();
                    break;
            }

            while(!thresholdMet){
                updateStatus("Executing algorithm...\n" +
                        "Hash function: " +  algorithm.getHashFunction().getClass().getSimpleName() +
                        "\nData set: " + entry.getKey() +
                        "\nOperation: " + operation +
                        "\nIteration: " + iterations);

                double result = strategy.execute(algorithm, entry.getValue(), testValues, baseline);
                totalResult += result;
                iterations++;
                double currentAverage = totalResult / iterations;



                if((iterations > 1 && Math.abs(currentAverage - previousAverage) < testConfig.getTestThreshold()) || iterations >= testConfig.getTestIterations()){
                    thresholdMet = true;
                } else {
                        previousAverage = currentAverage;
                }
            }
            writer.write(String.format(Locale.ENGLISH, "%s,%s,%d,%s,%d,%d,%s,%.2f\n",
                    algorithm.getClass().getSimpleName(),
                    algorithm.getHashFunction().getClass().getSimpleName(),
                    testConfig.getHashTableSize(),
                    entry.getKey(),
                    entry.getValue().length,
                    testConfig.getChunkSize(),
                    operation.toUpperCase(),
                    previousAverage));
        }
    }
}
