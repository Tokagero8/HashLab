package hashlab.tests;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HashTestConfigTest {

    @Test
    public void testToString() {
        HashTestConfig config = new HashTestConfig();

        config.setId("3fb4ce64-5596-44c8-bce4-c4901f49d1c3");
        config.setTestName("testName");
        config.setAlgorithm("LinearProbing");
        config.setHashTableSize(1000);
        config.setHashFunctions(Arrays.asList("MD5", "SHA1"));
        config.setPutSelected(true);
        config.setGetSelected(false);
        config.setDeleteSelected(true);
        config.setDataGenerated(true);
        config.setDataSize(500);
        config.setUniformSelected(true);
        config.setGaussianSelected(false);
        config.setExponentialSelected(true);
        config.setMean(5.0);
        config.setDeviation(1.0);
        config.setLambda(0.1);
        config.setBenchmarkIterations(100);
        config.setBenchmarkThreshold(0.05);

        String expected = "Id: 3fb4ce64-5596-44c8-bce4-c4901f49d1c3\n" +
                "Test name: testName\n" +
                "Algorithm: LinearProbing\n" +
                "Hash Table Size: 1000\n" +
                "Hash functions: [MD5, SHA1]\n" +
                "Operations: Put Delete\n" +
                "Data type: Generated\n" +
                "Data Size: 500\n" +
                "Data generation methods: Uniform Exponential\n" +
                "Data generation parameters: Lambda: 0.1\n" +
                "Number of benchmark iterations: 100\n" +
                "Benchmark threshold: 0.05";

        assertEquals(expected, config.toString());
    }

    @Test
    public void testGettersAndSetters() {
        HashTestConfig config = new HashTestConfig();

        config.setId("3fb4ce64-5596-44c8-bce4-c4901f49d1c3");
        assertEquals("3fb4ce64-5596-44c8-bce4-c4901f49d1c3", config.getId());

        config.setTestName("testName");
        assertEquals("testName", config.getTestName());

        config.setAlgorithm("LinearProbing");
        assertEquals("LinearProbing", config.getAlgorithm());

        config.setHashTableSize(1000);
        assertEquals(1000, config.getHashTableSize());

        List<String> hashFunctions = Arrays.asList("MD5", "SHA1");
        config.setHashFunctions(hashFunctions);
        assertEquals(hashFunctions, config.getHashFunctions());

        config.setPutSelected(true);
        assertTrue(config.isPutSelected());

        config.setGetSelected(false);
        assertFalse(config.isGetSelected());

        config.setDeleteSelected(true);
        assertTrue(config.isDeleteSelected());

        config.setDataGenerated(true);
        assertTrue(config.isDataGenerated());

        config.setDataSize(500);
        assertEquals(500, config.getDataSize());

        config.setUniformSelected(true);
        assertTrue(config.isUniformSelected());

        config.setGaussianSelected(false);
        assertFalse(config.isGaussianSelected());

        config.setExponentialSelected(true);
        assertTrue(config.isExponentialSelected());

        config.setMean(5.0);
        assertEquals(5.0, config.getMean());

        config.setDeviation(1.0);
        assertEquals(1.0, config.getDeviation());

        config.setLambda(0.1);
        assertEquals(0.1, config.getLambda());

        config.setBenchmarkIterations(100);
        assertEquals(100, config.getBenchmarkIterations());

        config.setBenchmarkThreshold(0.05);
        assertEquals(0.05, config.getBenchmarkThreshold());
    }
}
