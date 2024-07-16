package hashlab.algorithms.registry;

import hashlab.algorithms.collision_resolution.*;
import hashlab.algorithms.hash.MD5Hash;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HashRegistryTest {

    @Test
    void testRegisteredHashFunctions() {
        Set<String> hashFunctions = HashRegistry.getHashFunctions();
        assertNotNull(hashFunctions, "Hash functions set should not be null");
        assertTrue(hashFunctions.contains("MD5"), "Hash functions should contain 'MD5'");
        assertTrue(hashFunctions.contains("SHA1"), "Hash functions should contain 'SHA1'");
        assertTrue(hashFunctions.contains("SHA256"), "Hash functions should contain 'SHA256'");
        assertTrue(hashFunctions.contains("Jenkins Hash"), "Hash functions should contain 'Jenkins Hash'");
    }

    @Test
    void testRegisteredHashAlgorithms() {
        Set<String> algorithms = HashRegistry.getHashAlgorithms();
        assertNotNull(algorithms, "Algorithms set should not be null");
        assertTrue(algorithms.contains("BST"), "Algorithms should contain 'BST'");
        assertTrue(algorithms.contains("Linear Probing"), "Algorithms should contain 'Linear Probing'");
        assertTrue(algorithms.contains("Separate Chaining"), "Algorithms should contain 'Separate Chaining'");
        assertTrue(algorithms.contains("Quadratic Probing"), "Algorithms should contain 'Quadratic Probing'");
    }

    @Test
    void createAlgorithmWithKnownTypes() {
        HashAlgorithm<String, Integer> algorithm = HashRegistry.createAlgorithm("BST", "MD5", 10);
        assertNotNull(algorithm, "Algorithm should not be null");
        assertTrue(algorithm instanceof BSTHash, "Algorithm should be of type BSTHash");
        assertEquals(MD5Hash.class, algorithm.getHashFunction().getClass(), "Hash function should be of type MD5Hash");
    }

    @Test
    void createAlgorithmWithUnknownAlgorithmType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> HashRegistry.createAlgorithm("Unknown", "MD5", 10));
        assertEquals("Unknown algorithm type: Unknown", exception.getMessage(), "Exception message should indicate unknown algorithm type");
    }

    @Test
    void createAlgorithmWithUnknownFunctionType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> HashRegistry.createAlgorithm("BST", "Unknown", 10));
        assertEquals("Unknown hash function type: Unknown", exception.getMessage(), "Exception message should indicate unknown hash function type");
    }
}
