package hashlab.algorithms.registry;

import hashlab.algorithms.collision_resolution.HashAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HashRegistryTest {

    @Test
    void testRegisteredHashFunctions() {
        Set<String> hashFunctions = HashRegistry.getHashFunctions();
        assertTrue(hashFunctions.contains("MD5"));
        assertTrue(hashFunctions.contains("SHA1"));
        assertTrue(hashFunctions.contains("SHA256"));
    }

    @Test
    void testRegisteredHashAlgorithms() {
        Set<String> algorithms = HashRegistry.getHashAlgorithms();
        assertTrue(algorithms.contains("BST"));
        assertTrue(algorithms.contains("Linear Probing"));
        assertTrue(algorithms.contains("Separate Chaining"));
    }

    @Test
    void createAlgorithmWithKnownTypes() {
        HashAlgorithm<String, Integer> algorithm = HashRegistry.createAlgorithm("BST", "MD5", 10);
        assertNotNull(algorithm);
    }

    @Test
    void createAlgorithmWithUnknownAlgorithmType() {
        assertThrows(IllegalArgumentException.class, () -> HashRegistry.createAlgorithm("Unknown", "MD5", 10));
    }

    @Test
    void createAlgorithmWithUnknownFunctionType() {
        assertThrows(IllegalArgumentException.class, () -> HashRegistry.createAlgorithm("BST", "Unknown", 10));
    }
}
