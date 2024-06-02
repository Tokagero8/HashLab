package hashlab.algorithms.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SHA256HashTest {

    private final HashFunction sha256 = new SHA256Hash();

    @Test
    void sha256HashingConsistency() {
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertEquals(expectedHash, sha256.hash(input), "SHA-256 hash should be consistent for the input 'test'.");
    }

    @Test
    void sha256HashingWithEmptyString() {
        String input = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        assertEquals(expectedHash, sha256.hash(input), "SHA-256 hash should be consistent for an empty string.");
    }

    @Test
    void sha256HashingWithLongString() {
        String input = "This is a longer string to test the SHA-256 hashing function.";
        String expectedHash = "567457638b1a7a9c05d03c7d0e8ebb0c5515c903b4d1b39d93a59ccdef74f087";
        assertEquals(expectedHash, sha256.hash(input), "SHA-256 hash should be consistent for a longer string.");
    }

    @Test
    void sha256HashingCaseSensitivity() {
        String inputLowercase = "test";
        String inputUppercase = "TEST";
        String expectedHashLowercase = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String expectedHashUppercase = "94ee059335e587e501cc4bf90613e0814f00a7b08bc7c648fd865a2af6a22cc2";
        assertEquals(expectedHashLowercase, sha256.hash(inputLowercase), "SHA-256 hash should be case-sensitive for lowercase 'test'.");
        assertEquals(expectedHashUppercase, sha256.hash(inputUppercase), "SHA-256 hash should be case-sensitive for uppercase 'TEST'.");
    }

    @Test
    void sha256HashingWithSpecialCharacters() {
        String input = "!@#$%^&*()_+[];'./,<>?:\"{}|";
        String expectedHash = "4315615e0d6ab6112ac35d311692c6db713ec08a8eef761c9fdd624aff1503be";
        assertEquals(expectedHash, sha256.hash(input), "SHA-256 hash should be consistent for special characters.");
    }

    @Test
    void sha256HashingWithNullInput() {
        assertThrows(NullPointerException.class, () -> sha256.hash(null), "Hashing null input should throw NullPointerException.");
    }
}
