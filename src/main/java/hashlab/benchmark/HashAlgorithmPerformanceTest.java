package hashlab.benchmark;

import hashlab.algorithms.HashAlgorithm;

import java.util.function.Consumer;

public class HashAlgorithmPerformanceTest<Key, Value> {

    private final HashAlgorithm<Key, Value> algorithm;
    private final Key[] testKeys;
    private final Value[] testValues;

    public HashAlgorithmPerformanceTest(HashAlgorithm<Key, Value> algorithm, Key[] testKeys, Value[] testValues) {
        this.algorithm = algorithm;
        this.testKeys = testKeys;
        this.testValues = testValues;
    }

    public double runTest(String operation, double baseline) {
        Consumer<HashAlgorithm<Key, Value>> testOperation;

        switch (operation.toLowerCase()) {
            case "put":
                testOperation = this::testPut;
                break;
            case "get":
                testOperation = this::testGet;
                break;
            case "delete":
                testOperation = this::testDelete;
                break;
            default:
                throw new IllegalArgumentException("Nieznana operacja: " + operation);
        }

        double duration = measurePerformance(testOperation);
        return duration / baseline;
    }

    private double measurePerformance(Consumer<HashAlgorithm<Key, Value>> testOperation) {
        long startTime = System.nanoTime();
        testOperation.accept(algorithm);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private void testPut(HashAlgorithm<Key, Value> algorithm) {
        for (int i = 0; i < testKeys.length; i++) {
            algorithm.put(testKeys[i], testValues[i]);
        }
    }

    private void testGet(HashAlgorithm<Key, Value> algorithm) {
        for (Key key : testKeys) {
            algorithm.get(key);
        }
    }

    private void testDelete(HashAlgorithm<Key, Value> algorithm) {
        for (Key key : testKeys) {
            algorithm.delete(key);
        }
    }
}