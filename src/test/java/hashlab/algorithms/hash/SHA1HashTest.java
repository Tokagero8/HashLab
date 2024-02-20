package hashlab.algorithms.hash;

import hashlab.algorithms.hash.HashFunction;
import hashlab.algorithms.hash.SHA1Hash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SHA1HashTest {

    @Test
    void sha1HashingConsistency() {
        HashFunction sha1 = new SHA1Hash();
        String input = "test";
        String expectedHash = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        assertEquals(expectedHash, sha1.hash(input));
    }
}
