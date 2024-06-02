package hashlab.algorithms.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SHA1HashTest {

    private final HashFunction sha1 = new SHA1Hash();

    @Test
    void sha1HashingConsistency() {
        String input = "test";
        String expectedHash = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        assertEquals(expectedHash, sha1.hash(input), "SHA-1 hash should be consistent for the input 'test'.");
    }

    @Test
    void sha1HashingWithEmptyString() {
        String input = "";
        String expectedHash = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
        assertEquals(expectedHash, sha1.hash(input), "SHA-1 hash should be consistent for an empty string.");
    }

    @Test
    void sha1HashingWithLongString() {
        String input = "This is a longer string to test the SHA-1 hashing function.";
        String expectedHash = "c38d10250659c2c582e5a9d484206d8a1ac09003";
        assertEquals(expectedHash, sha1.hash(input), "SHA-1 hash should be consistent for a longer string.");
    }

    @Test
    void sha1HashingCaseSensitivity() {
        String inputLowercase = "test";
        String inputUppercase = "TEST";
        String expectedHashLowercase = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        String expectedHashUppercase = "984816fd329622876e14907634264e6f332e9fb3";
        assertEquals(expectedHashLowercase, sha1.hash(inputLowercase), "SHA-1 hash should be case-sensitive for lowercase 'test'.");
        assertEquals(expectedHashUppercase, sha1.hash(inputUppercase), "SHA-1 hash should be case-sensitive for uppercase 'TEST'.");
    }

    @Test
    void sha1HashingWithSpecialCharacters() {
        String input = "!@#$%^&*()_+[];'./,<>?:\"{}|";
        String expectedHash = "3c45b8913f76ec7de67ea6023994d58da1f52ba7";
        assertEquals(expectedHash, sha1.hash(input), "SHA-1 hash should be consistent for special characters.");
    }

    @Test
    void sha1HashingWithNullInput() {
        assertThrows(NullPointerException.class, () -> sha1.hash(null), "Hashing null input should throw NullPointerException.");
    }
}
