package hashlab.functions;

import hashlab.algorithms.hash.HashFunction;
import hashlab.algorithms.hash.SHA256Hash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SHA256HashTest {

    @Test
    void sha256HashingConsistency() {
        HashFunction sha256 = new SHA256Hash();
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertEquals(expectedHash, sha256.hash(input));
    }
}
