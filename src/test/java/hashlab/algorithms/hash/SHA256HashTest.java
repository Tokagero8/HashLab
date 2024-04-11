package hashlab.algorithms.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SHA256HashTest {

    @Test
    void sha256HashingConsistency() {
        HashFunction sha256 = new SHA256Hash();
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertEquals(expectedHash, sha256.hash(input));
    }

    @Test
    void sha256HashingWithEmptyString() {
        HashFunction sha256 = new SHA256Hash();
        String input = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        assertEquals(expectedHash, sha256.hash(input));
    }

    @Test
    void sha256HashingWithLongString() {
        HashFunction sha256 = new SHA256Hash();
        String input = "This is a longer string to test the SHA-256 hashing function.";
        String expectedHash = "567457638b1a7a9c05d03c7d0e8ebb0c5515c903b4d1b39d93a59ccdef74f087";
        assertEquals(expectedHash, sha256.hash(input));
    }

    @Test
    void sha256HashingCaseSensitivity() {
        HashFunction sha256 = new SHA256Hash();
        String inputLowercase = "test";
        String inputUppercase = "TEST";
        String expectedHashLowercase = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String expectedHashUppercase = "94ee059335e587e501cc4bf90613e0814f00a7b08bc7c648fd865a2af6a22cc2";
        assertEquals(expectedHashLowercase, sha256.hash(inputLowercase));
        assertEquals(expectedHashUppercase, sha256.hash(inputUppercase));
    }

    @Test
    void sha256HashingWithSpecialCharacters() {
        HashFunction sha256 = new SHA256Hash();
        String input = "!@#$%^&*()_+[];'./,<>?:\"{}|";
        String expectedHash = "4315615e0d6ab6112ac35d311692c6db713ec08a8eef761c9fdd624aff1503be";
        assertEquals(expectedHash, sha256.hash(input));
    }

    @Test
    void sha256HashingWithNullInput() {
        HashFunction sha256 = new SHA256Hash();
        assertThrows(NullPointerException.class, () -> sha256.hash(null));
    }
}
