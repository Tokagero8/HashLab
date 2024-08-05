package hashlab.benchmark;

import hashlab.algorithms.collision_resolution.BSTHash;
import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.algorithms.hash.MD5Hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashAlgorithmPerformanceUnitTest {

    private HashAlgorithm<String, Integer> algorithm;
    private String[] testKeys;
    private Integer[] testValues;
    private HashAlgorithmPerformanceTest<String, Integer> performanceTest;
    private double baseline;

    @BeforeEach
    void setUp() {
        algorithm = new BSTHash<>(100, new MD5Hash());
        testKeys = new String[]{"key1", "key2", "key3", "key4", "key5"};
        testValues = new Integer[]{1, 2, 3, 4, 5};
        performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, testKeys, testValues);
        baseline = Benchmark.calculateBaseline(100, 0.01);
    }

    @Test
    void testPutOperationPerformance() {
        double result = performanceTest.runTest("put", baseline);
        assertTrue(result > 0, "Performance ratio for 'put' should be greater than 0");
    }

    @Test
    void testGetOperationPerformance() {
        for (int i = 0; i < testKeys.length; i++) {
            algorithm.put(testKeys[i], testValues[i]);
        }
        double result = performanceTest.runTest("get", baseline);
        assertTrue(result > 0, "Performance ratio for 'get' should be greater than 0");
    }

    @Test
    void testDeleteOperationPerformance() {
        for (int i = 0; i < testKeys.length; i++) {
            algorithm.put(testKeys[i], testValues[i]);
        }
        double result = performanceTest.runTest("delete", baseline);
        assertTrue(result > 0, "Performance ratio for 'delete' should be greater than 0");
    }

    @Test
    void testUnknownOperation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> performanceTest.runTest("unknown", baseline));
        assertEquals("Unknown operation: unknown", exception.getMessage(), "Exception message should indicate unknown operation");
    }

    @Test
    void testEmptyKeyValueArrays() {
        String[] emptyKeys = new String[]{};
        Integer[] emptyValues = new Integer[]{};
        HashAlgorithmPerformanceTest<String, Integer> emptyTest = new HashAlgorithmPerformanceTest<>(algorithm, emptyKeys, emptyValues);

        double putResult = emptyTest.runTest("put", baseline);
        assertTrue(putResult >= 0, "Performance ratio for 'put' with empty arrays should be >= 0");

        double getResult = emptyTest.runTest("get", baseline);
        assertTrue(getResult >= 0, "Performance ratio for 'get' with empty arrays should be >= 0");

        double deleteResult = emptyTest.runTest("delete", baseline);
        assertTrue(deleteResult >= 0, "Performance ratio for 'delete' with empty arrays should be >= 0");
    }

    @Test
    void testSingleElementKeyValueArrays() {
        String[] singleKey = new String[]{"singleKey"};
        Integer[] singleValue = new Integer[]{1};
        HashAlgorithmPerformanceTest<String, Integer> singleTest = new HashAlgorithmPerformanceTest<>(algorithm, singleKey, singleValue);

        double putResult = singleTest.runTest("put", baseline);
        assertTrue(putResult > 0, "Performance ratio for 'put' with single element arrays should be greater than 0");

        algorithm.put(singleKey[0], singleValue[0]);
        double getResult = singleTest.runTest("get", baseline);
        assertTrue(getResult > 0, "Performance ratio for 'get' with single element arrays should be greater than 0");

        double deleteResult = singleTest.runTest("delete", baseline);
        assertTrue(deleteResult > 0, "Performance ratio for 'delete' with single element arrays should be greater than 0");
    }

    @Test
    void testPerformanceWithLargeData() {
        int largeSize = 10000;
        String[] largeKeys = new String[largeSize];
        Integer[] largeValues = new Integer[largeSize];
        for (int i = 0; i < largeSize; i++) {
            largeKeys[i] = "key" + i;
            largeValues[i] = i;
        }
        HashAlgorithmPerformanceTest<String, Integer> largeTest = new HashAlgorithmPerformanceTest<>(algorithm, largeKeys, largeValues);

        double putResult = largeTest.runTest("put", baseline);
        assertTrue(putResult > 0, "Performance ratio for 'put' with large data should be greater than 0");

        for (int i = 0; i < largeSize; i++) {
            algorithm.put(largeKeys[i], largeValues[i]);
        }
        double getResult = largeTest.runTest("get", baseline);
        assertTrue(getResult > 0, "Performance ratio for 'get' with large data should be greater than 0");

        double deleteResult = largeTest.runTest("delete", baseline);
        assertTrue(deleteResult > 0, "Performance ratio for 'delete' with large data should be greater than 0");
    }

    @Test
    void testNullKey() {
        String[] nullKey = new String[]{null};
        Integer[] value = new Integer[]{1};
        HashAlgorithmPerformanceTest<String, Integer> nullKeyTest = new HashAlgorithmPerformanceTest<>(algorithm, nullKey, value);

        assertThrows(NullPointerException.class, () -> nullKeyTest.runTest("put", baseline));
    }

    @Test
    void testNullValue() {
        String[] key = new String[]{"key1"};
        Integer[] nullValue = new Integer[]{null};
        HashAlgorithmPerformanceTest<String, Integer> nullValueTest = new HashAlgorithmPerformanceTest<>(algorithm, key, nullValue);

        double putResult = nullValueTest.runTest("put", baseline);
        assertTrue(putResult > 0, "Performance ratio for 'put' with null value should be greater than 0");

        double getResult = nullValueTest.runTest("get", baseline);
        assertTrue(getResult > 0, "Performance ratio for 'get' with null value should be greater than 0");

        double deleteResult = nullValueTest.runTest("delete", baseline);
        assertTrue(deleteResult > 0, "Performance ratio for 'delete' with null value should be greater than 0");
    }
}
