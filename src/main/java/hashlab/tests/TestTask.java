package hashlab.tests;

import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.benchmark.Benchmark;
import hashlab.benchmark.HashAlgorithmPerformanceTest;
import hashlab.core.HashAlgorithmFactory;
import hashlab.utils.DataGenerator;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestTask extends Task<Void> {

    private final String resultFileName;
    private final List<HashTestConfig> selectedTests;

    public TestTask(String resultFileName,  List<HashTestConfig> selectedTests) {
        this.resultFileName = resultFileName;
        this.selectedTests = selectedTests;
    }

    @Override
    protected Void call() throws Exception {

        String resultFilePath = resultFileName + ".csv";
        File resultFile = new File(resultFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile, true))){
            if (!resultFile.exists() || resultFile.length() == 0) {
                writer.write("Algorithm,Function,Table Size,Data Type,Data Size,Operation,Result\n");
            }

            int totalTests = selectedTests.size();
            int currentTestIndex = 0;

            for(HashTestConfig testConfig : selectedTests) {
                if (isCancelled()) {
                    break;
                }

                updateMessage("Running test: " + testConfig.getTestName());

                List<Map.Entry<String, String[]>> testKeysSets = getOrGenerateTestKeys(testConfig);

                int totalKeysSetsSize = testKeysSets.stream().mapToInt(entry -> entry.getValue().length).sum();
                Integer[] testValues = new Integer[totalKeysSetsSize];
                Arrays.fill(testValues, 1);

                double baseline = Benchmark.calculateBaseline(testConfig.getBenchmarkIterations(), testConfig.getBenchmarkThreshold());

                List<HashAlgorithm<String, Integer>> algorithms = createHashAlgorithms(testConfig);

                for (HashAlgorithm<String, Integer> algorithm : algorithms) {
                    for (Map.Entry<String, String[]> entry : testKeysSets) {
                        String dataType = entry.getKey();
                        String[] testKeysSet = entry.getValue();
                        HashAlgorithmPerformanceTest<String, Integer> performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, testKeysSet, testValues);

                        performAndWriteTest("put", testConfig, algorithm, dataType, entry.getValue().length, baseline, performanceTest, writer);
                        performAndWriteTest("get", testConfig, algorithm, dataType, entry.getValue().length, baseline, performanceTest, writer);
                        performAndWriteTest("delete", testConfig, algorithm, dataType, entry.getValue().length, baseline, performanceTest, writer);
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

    private void performAndWriteTest(String operation, HashTestConfig testConfig, HashAlgorithm<String, Integer> algorithm, String dataType, int dataSize, double baseline, HashAlgorithmPerformanceTest<String, Integer> performanceTest, BufferedWriter writer) throws IOException {
        if (operation.equals("put") && testConfig.isPutSelected() || operation.equals("get") && testConfig.isGetSelected() || operation.equals("delete") && testConfig.isDeleteSelected()) {
            double result = performanceTest.runTest(operation, baseline);
            writer.write(String.format(Locale.ENGLISH, "%s,%s,%d,%s,%d,%s,%.2f\n",
                    algorithm.getClass().getSimpleName(),
                    algorithm.getHashFunction().getClass().getSimpleName(),
                    testConfig.getHashTableSize(),
                    dataType,
                    dataSize,
                    operation.toUpperCase(),
                    result));
        }
    }


}
