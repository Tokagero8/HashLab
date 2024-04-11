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
    void testToString() {
        HashTestConfig config = new HashTestConfig();

        config.setId("testId");
        config.setTestName("SampleTest");
        config.setAlgorithm("SHA-256");
        config.setHashTableSize(100);
        config.setChunkSize(5);
        config.setHashFunctions(Arrays.asList("MD5", "SHA1"));
        config.setPutSelected(true);
        config.setGetSelected(false);
        config.setDeleteSelected(true);
        config.setDataGenerated(true);
        config.setGeneratedOnAdd(true);
        config.setSelectedFilePath("path/to/data");
        config.setLoadedOnAdd(false);
        config.setDataSize(200);
        config.setUniformSelected(true);
        config.setGaussianSelected(true);
        config.setExponentialSelected(false);
        config.setMean(0.0);
        config.setDeviation(1.0);
        config.setBenchmarkIterations(10);
        config.setBenchmarkThreshold(0.01);
        config.setTestIterations(5);
        config.setTestThreshold(0.005);
        config.setWarmupIterations(2);

        String expected = "Id: testId\n" +
                "Test name: SampleTest\n" +
                "Algorithm: SHA-256\n" +
                "Hash Table Size: 100\n" +
                "Chunk Size: 5\n" +
                "Hash functions: [MD5, SHA1]\n" +
                "Operations: Put Delete\n" +
                "Data type: Generated on a test add\n" +
                "Data Size: 200\n" +
                "Data generation methods: Uniform Gaussian\n" +
                "Data generation parameters: Mean: 0.0, Deviation: 1.0;\n" +
                "Number of benchmark iterations: 10\n" +
                "Benchmark threshold: 0.01\n" +
                "Test iterations: 5\n" +
                "Test threshold: 0.005\n" +
                "Warmup iterations: 2";

        assertEquals(expected, config.toString().trim(), "The toString method should return the expected string representation.");
    }


    @Test
    void testGettersAndSetters() {
        HashTestConfig config = new HashTestConfig();

        String id = "testId";
        config.setId(id);
        String testName = "MyTest";
        config.setTestName(testName);
        String algorithm = "SHA256";
        config.setAlgorithm(algorithm);
        int hashTableSize = 100;
        config.setHashTableSize(hashTableSize);
        int chunkSize = 10;
        config.setChunkSize(chunkSize);
        List<String> hashFunctions = Arrays.asList("MD5", "SHA1");
        config.setHashFunctions(hashFunctions);
        config.setPutSelected(true);
        config.setGetSelected(true);
        config.setDeleteSelected(false);
        config.setDataGenerated(true);
        config.setGeneratedOnAdd(true);
        String selectedFilePath = "/path/to/file";
        config.setSelectedFilePath(selectedFilePath);
        config.setLoadedOnAdd(false);
        int dataSize = 1000;
        config.setDataSize(dataSize);
        config.setUniformSelected(true);
        config.setGaussianSelected(false);
        config.setExponentialSelected(true);
        double mean = 0.0;
        config.setMean(mean);
        double deviation = 1.0;
        config.setDeviation(deviation);
        double lambda = 0.5;
        config.setLambda(lambda);
        String uniformDataString = "uniformData";
        config.setUniformDataString(uniformDataString);
        String gaussianDataString = "gaussianData";
        config.setGaussianDataString(gaussianDataString);
        String exponentialDataString = "exponentialData";
        config.setExponentialDataString(exponentialDataString);
        String loadedDataString = "loadedData";
        config.setLoadedDataString(loadedDataString);
        int benchmarkIterations = 10;
        config.setBenchmarkIterations(benchmarkIterations);
        double benchmarkThreshold = 0.01;
        config.setBenchmarkThreshold(benchmarkThreshold);
        int testIterations = 5;
        config.setTestIterations(testIterations);
        double testThreshold = 0.01;
        config.setTestThreshold(testThreshold);
        int warmupIterations = 2;
        config.setWarmupIterations(warmupIterations);

        assertEquals("testId", config.getId(), "ID should match the set value.");
        assertEquals("MyTest", config.getTestName(), "Test name should match the set value.");
        assertEquals("SHA256", config.getAlgorithm(), "Algorithm should match the set value.");
        assertEquals(100, config.getHashTableSize(), "Hash table size should match the set value.");
        assertEquals(10, config.getChunkSize(), "Chunk size should match the set value.");
        assertEquals(Arrays.asList("MD5", "SHA1"), config.getHashFunctions(), "Hash functions list should match the set value.");
        assertTrue(config.isPutSelected(), "Put operation selection should be true.");
        assertTrue(config.isGetSelected(), "Get operation selection should be true.");
        assertFalse(config.isDeleteSelected(), "Delete operation selection should be false.");
        assertTrue(config.isDataGenerated(), "Data generation flag should be true.");
        assertTrue(config.isGeneratedOnAdd(), "Generated on add flag should be true.");
        assertEquals("/path/to/file", config.getSelectedFilePath(), "Selected file path should match the set value.");
        assertFalse(config.isLoadedOnAdd(), "Loaded on add flag should be false.");
        assertEquals(1000, config.getDataSize(), "Data size should match the set value.");
        assertTrue(config.isUniformSelected(), "Uniform data selection should be true.");
        assertFalse(config.isGaussianSelected(), "Gaussian data selection should be false.");
        assertTrue(config.isExponentialSelected(), "Exponential data selection should be true.");
        assertEquals(0.0, config.getMean(), 0.01, "Mean value should match the set value.");
        assertEquals(1.0, config.getDeviation(), 0.01, "Deviation value should match the set value.");
        assertEquals(0.5, config.getLambda(), 0.01, "Lambda value should match the set value.");
        assertEquals("uniformData", config.getUniformDataString(), "Uniform data string should match the set value.");
        assertEquals("gaussianData", config.getGaussianDataString(), "Gaussian data string should match the set value.");
        assertEquals("exponentialData", config.getExponentialDataString(), "Exponential data string should match the set value.");
        assertEquals("loadedData", config.getLoadedDataString(), "Loaded data string should match the set value.");
        assertEquals(10, config.getBenchmarkIterations(), "Benchmark iterations should match the set value.");
        assertEquals(0.01, config.getBenchmarkThreshold(), 0.001, "Benchmark threshold should match the set value.");
        assertEquals(5, config.getTestIterations(), "Test iterations should match the set value.");
        assertEquals(0.01, config.getTestThreshold(), 0.001, "Test threshold should match the set value.");
        assertEquals(2, config.getWarmupIterations(), "Warmup iterations should match the set value.");
    }
}
