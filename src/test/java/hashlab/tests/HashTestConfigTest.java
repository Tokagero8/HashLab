package hashlab.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HashTestConfigTest {

    private HashTestConfig config;

    @BeforeEach
    void setUp() {
        config = new HashTestConfig();
    }

    @Test
    void testId() {
        config.setId("testId");
        assertEquals("testId", config.getId(), "Id should be 'testId'");
    }

    @Test
    void testTestName() {
        config.setTestName("testName");
        assertEquals("testName", config.getTestName(), "Test name should be 'testName'");
    }

    @Test
    void testAlgorithm() {
        config.setAlgorithm("BST");
        assertEquals("BST", config.getAlgorithm(), "Algorithm should be 'BST'");
    }

    @Test
    void testHashTableSize() {
        config.setHashTableSize(100);
        assertEquals(100, config.getHashTableSize(), "Hash table size should be 100");
    }

    @Test
    void testChunkSize() {
        config.setChunkSize(10);
        assertEquals(10, config.getChunkSize(), "Chunk size should be 10");
    }

    @Test
    void testHashFunctions() {
        List<String> hashFunctions = Arrays.asList("MD5", "SHA1");
        config.setHashFunctions(hashFunctions);
        assertEquals(hashFunctions, config.getHashFunctions(), "Hash functions should match the set list");
    }

    @Test
    void testPutSelected() {
        config.setPutSelected(true);
        assertTrue(config.isPutSelected(), "Put selected should be true");
    }

    @Test
    void testGetSelected() {
        config.setGetSelected(true);
        assertTrue(config.isGetSelected(), "Get selected should be true");
    }

    @Test
    void testDeleteSelected() {
        config.setDeleteSelected(true);
        assertTrue(config.isDeleteSelected(), "Delete selected should be true");
    }

    @Test
    void testDataGenerated() {
        config.setDataGenerated(true);
        assertTrue(config.isDataGenerated(), "Data generated should be true");
    }

    @Test
    void testGeneratedOnAdd() {
        config.setGeneratedOnAdd(true);
        assertTrue(config.isGeneratedOnAdd(), "Generated on add should be true");
    }

    @Test
    void testSelectedFilePath() {
        config.setSelectedFilePath("path/to/file");
        assertEquals("path/to/file", config.getSelectedFilePath(), "Selected file path should be 'path/to/file'");
    }

    @Test
    void testLoadedOnAdd() {
        config.setLoadedOnAdd(true);
        assertTrue(config.isLoadedOnAdd(), "Loaded on add should be true");
    }

    @Test
    void testDataSize() {
        config.setDataSize(1000);
        assertEquals(1000, config.getDataSize(), "Data size should be 1000");
    }

    @Test
    void testUniformSelected() {
        config.setUniformSelected(true);
        assertTrue(config.isUniformSelected(), "Uniform selected should be true");
    }

    @Test
    void testGaussianSelected() {
        config.setGaussianSelected(true);
        assertTrue(config.isGaussianSelected(), "Gaussian selected should be true");
    }

    @Test
    void testExponentialSelected() {
        config.setExponentialSelected(true);
        assertTrue(config.isExponentialSelected(), "Exponential selected should be true");
    }

    @Test
    void testMean() {
        config.setMean(5.0);
        assertEquals(5.0, config.getMean(), "Mean should be 5.0");
    }

    @Test
    void testDeviation() {
        config.setDeviation(1.0);
        assertEquals(1.0, config.getDeviation(), "Deviation should be 1.0");
    }

    @Test
    void testLambda() {
        config.setLambda(0.5);
        assertEquals(0.5, config.getLambda(), "Lambda should be 0.5");
    }

    @Test
    void testUniformDataString() {
        config.setUniformDataString("uniformData");
        assertEquals("uniformData", config.getUniformDataString(), "Uniform data string should be 'uniformData'");
    }

    @Test
    void testGaussianDataString() {
        config.setGaussianDataString("gaussianData");
        assertEquals("gaussianData", config.getGaussianDataString(), "Gaussian data string should be 'gaussianData'");
    }

    @Test
    void testExponentialDataString() {
        config.setExponentialDataString("exponentialData");
        assertEquals("exponentialData", config.getExponentialDataString(), "Exponential data string should be 'exponentialData'");
    }

    @Test
    void testLoadedDataString() {
        config.setLoadedDataString("loadedData");
        assertEquals("loadedData", config.getLoadedDataString(), "Loaded data string should be 'loadedData'");
    }

    @Test
    void testBenchmarkIterations() {
        config.setBenchmarkIterations(10);
        assertEquals(10, config.getBenchmarkIterations(), "Benchmark iterations should be 10");
    }

    @Test
    void testBenchmarkThreshold() {
        config.setBenchmarkThreshold(0.1);
        assertEquals(0.1, config.getBenchmarkThreshold(), "Benchmark threshold should be 0.1");
    }

    @Test
    void testTestIterations() {
        config.setTestIterations(20);
        assertEquals(20, config.getTestIterations(), "Test iterations should be 20");
    }

    @Test
    void testTestThreshold() {
        config.setTestThreshold(0.2);
        assertEquals(0.2, config.getTestThreshold(), "Test threshold should be 0.2");
    }

    @Test
    void testWarmupIterations() {
        config.setWarmupIterations(5);
        assertEquals(5, config.getWarmupIterations(), "Warmup iterations should be 5");
    }

    @Test
    void testToString() {
        config.setId("testId");
        config.setTestName("testName");
        config.setAlgorithm("BST");
        config.setHashTableSize(100);
        config.setChunkSize(10);
        config.setHashFunctions(Arrays.asList("MD5", "SHA1"));
        config.setPutSelected(true);
        config.setGetSelected(true);
        config.setDeleteSelected(true);
        config.setDataGenerated(true);
        config.setGeneratedOnAdd(true);
        config.setSelectedFilePath("path/to/file");
        config.setLoadedOnAdd(true);
        config.setDataSize(1000);
        config.setUniformSelected(true);
        config.setGaussianSelected(true);
        config.setExponentialSelected(true);
        config.setMean(5.0);
        config.setDeviation(1.0);
        config.setLambda(0.5);
        config.setUniformDataString("uniformData");
        config.setGaussianDataString("gaussianData");
        config.setExponentialDataString("exponentialData");
        config.setLoadedDataString("loadedData");
        config.setBenchmarkIterations(10);
        config.setBenchmarkThreshold(0.1);
        config.setTestIterations(20);
        config.setTestThreshold(0.2);
        config.setWarmupIterations(5);

        String expectedString = "Id: testId\n" +
                "Test name: testName\n" +
                "Algorithm: BST\n" +
                "Hash Table Size: 100\n" +
                "Chunk Size: 10\n" +
                "Hash functions: [MD5, SHA1]\n" +
                "Operations: Put Get Delete\n" +
                "Data type: Generated on a test add\n" +
                "Data Size: 1000\n" +
                "Data generation methods: Uniform Gaussian Exponential\n" +
                "Data generation parameters: Mean: 5.0, Deviation: 1.0; Lambda: 0.5\n" +
                "Number of benchmark iterations: 10\n" +
                "Benchmark threshold: 0.1\n" +
                "Test iterations: 20\n" +
                "Test threshold: 0.2\n" +
                "Warmup iterations: 5";

        assertEquals(expectedString, config.toString(), "toString output should match the expected string");
    }
}
