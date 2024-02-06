package hashlab.tests;

import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.benchmark.Benchmark;
import hashlab.benchmark.HashAlgorithmPerformanceTest;
import hashlab.core.HashAlgorithmFactory;
import hashlab.utils.DataGenerator;
import javafx.concurrent.Task;

import java.io.*;
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

                List<HashAlgorithm<String, Integer>> algorithms = createHashAlgorithms(testConfig);
                List<Map.Entry<String, String[]>> testKeysSets;
                if (testConfig.isDataGenerated) {
                    testKeysSets = generateTestKeys(testConfig);
                } else {
                    testKeysSets = loadDataFromFile(testConfig.getselectedFilePath());
                }

                int totalKeysSetsSize = testKeysSets.stream().mapToInt(entry -> entry.getValue().length).sum();
                Integer[] testValues = new Integer[totalKeysSetsSize];
                Arrays.fill(testValues, 1);

                double baseline = Benchmark.calculateBaseline(testConfig.benchmarkIterations, testConfig.benchmarkThreshold);

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
            e.printStackTrace();
        }
        return null;
    }

    private List<HashAlgorithm<String, Integer>> createHashAlgorithms(HashTestConfig testConfig) {
        List<HashAlgorithm<String, Integer>> algorithms = new ArrayList<>();
        for (String hashFunction : testConfig.hashFunctions) {
            HashAlgorithm<String, Integer> algorithm = HashAlgorithmFactory.createAlgorithm(testConfig.algorithm, hashFunction, testConfig.hashTableSize);
            algorithms.add(algorithm);
        }
        return algorithms;
    }

    private List<Map.Entry<String, String[]>> generateTestKeys(HashTestConfig testConfig) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();

        if (testConfig.uniformSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateUniformValues(testConfig.min, testConfig.max, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Uniform", keys));
        }
        if (testConfig.gaussianSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateGaussianValues(testConfig.mean, testConfig.deviation, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Gaussian", keys));
        }
        if (testConfig.exponentialSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateExponentialValues(testConfig.lambda, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Exponential", keys));
        }

        return testKeysSets;
    }

    private String[] convertDoubleArrayToStringArray(double[] doubleArray) {
        String[] stringArray = new String[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            stringArray[i] = String.valueOf(doubleArray[i]);
        }
        return stringArray;
    }

    private void performAndWriteTest(String operation, HashTestConfig testConfig, HashAlgorithm<String, Integer> algorithm, String dataType, int dataSize, double baseline, HashAlgorithmPerformanceTest<String, Integer> performanceTest, BufferedWriter writer) throws IOException {
        if (operation.equals("put") && testConfig.put || operation.equals("get") && testConfig.get || operation.equals("delete") && testConfig.delete) {
            double result = performanceTest.runTest(operation, baseline);
            writer.write(String.format(Locale.ENGLISH, "%s,%s,%d,%s,%d,%s,%.2f\n",
                    algorithm.getClass().getSimpleName(),
                    algorithm.getHashFunction().getClass().getSimpleName(),
                    testConfig.hashTableSize,
                    dataType,
                    dataSize,
                    operation.toUpperCase(),
                    result));
        }
    }

    private List<Map.Entry<String, String[]>> loadDataFromFile(String selectedFilePath) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();
        File file = new File(selectedFilePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> keys = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                keys.add(line);
            }
            testKeysSets.add(new AbstractMap.SimpleEntry<>("FromFile", keys.toArray(new String[0])));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return testKeysSets;
    }
}
