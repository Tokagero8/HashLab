package hashlab.integration;

import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.algorithms.hash.HashFunction;
import hashlab.algorithms.registry.HashRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashAlgorithmsIntegrationTest {

    @BeforeAll
    static void setup() {

    }

    @Test
    void testIntegrationOfAlgorithmsAndFunctions() {
        String[] algorithms = {"BST", "Linear Probing", "Separate Chaining"};
        String[] functions = {"MD5", "SHA1", "SHA256"};

        for (String algorithm : algorithms) {
            for (String function : functions) {
                HashAlgorithm<String, Integer> hashAlgorithm = HashRegistry.createAlgorithm(algorithm, function, 10);
                HashFunction hashFunction = hashAlgorithm.getHashFunction();

                hashAlgorithm.put("key1", 1);
                hashAlgorithm.put("key2", 2);

                assertEquals(1, hashAlgorithm.get("key1"));
                assertEquals(2, hashAlgorithm.get("key2"));

                hashAlgorithm.reset();
                assertNull(hashAlgorithm.get("key1"));
                assertNull(hashAlgorithm.get("key2"));

                assertNotNull(hashFunction.hash("test"));
            }
        }
    }
}
