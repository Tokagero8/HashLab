package hashlab.algorithms.registry;

import hashlab.algorithms.collision_resolution.*;
import hashlab.algorithms.hash.JenkinsHash;
import hashlab.algorithms.hash.MD5Hash;
import hashlab.algorithms.hash.SHA1Hash;
import hashlab.algorithms.hash.SHA256Hash;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HashRegistryTest {

    @BeforeAll
    static void setUp() {
        HashRegistry.registerHashAlgorithm("BST", BSTHash::new);
        HashRegistry.registerHashAlgorithm("Linear Probing", LinearProbingHash::new);
        HashRegistry.registerHashAlgorithm("Separate Chaining", SeparateChainingHash::new);
        HashRegistry.registerHashAlgorithm("Quadratic Probing", QuadraticProbingHash::new);

        HashRegistry.registerHashFunction("MD5", v -> new MD5Hash());
        HashRegistry.registerHashFunction("SHA1", v -> new SHA1Hash());
        HashRegistry.registerHashFunction("SHA256", v -> new SHA256Hash());
        HashRegistry.registerHashFunction("Jenkins Hash", v -> new JenkinsHash());
    }

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

    @Test
    void registerAndCreateNewAlgorithm() {
        HashRegistry.registerHashAlgorithm("New Algorithm", (size, function) -> new LinearProbingHash<>(size, function));
        HashAlgorithm<String, Integer> algorithm = HashRegistry.createAlgorithm("New Algorithm", "MD5", 10);
        assertNotNull(algorithm, "Algorithm should not be null");
        assertTrue(algorithm instanceof LinearProbingHash, "Algorithm should be of type LinearProbingHash");
    }

    @Test
    void registerAndCreateNewHashFunction() {
        HashRegistry.registerHashFunction("NewHashFunction", v -> input -> "newhash");
        HashAlgorithm<String, Integer> algorithm = HashRegistry.createAlgorithm("BST", "NewHashFunction", 10);
        assertNotNull(algorithm, "Algorithm should not be null");
        assertEquals("newhash", algorithm.getHashFunction().hash("test"), "Hash function should return 'newhash' for any input");
    }
}
