package hashlab.algorithms.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MD5HashTest {

    private final HashFunction md5 = new MD5Hash();

    @Test
    void md5HashingConsistency() {
        String input = "test";
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(expectedHash, md5.hash(input), "MD5 hash should be consistent for the input 'test'.");
    }

    @Test
    void md5HashingWithEmptyString() {
        String input = "";
        String expectedHash = "d41d8cd98f00b204e9800998ecf8427e";
        assertEquals(expectedHash, md5.hash(input), "MD5 hash should be consistent for an empty string.");
    }

    @Test
    void md5HashingWithLongString() {
        String input = "This is a longer string to test the MD5 hashing function.";
        String expectedHash = "a44871c7d14322299172d7a1b2b0fd33";
        assertEquals(expectedHash, md5.hash(input), "MD5 hash should be consistent for a longer string.");
    }

    @Test
    void md5HashingCaseSensitivity() {
        String inputLowercase = "test";
        String inputUppercase = "TEST";
        String expectedHashLowercase = "098f6bcd4621d373cade4e832627b4f6";
        String expectedHashUppercase = "033bd94b1168d7e4f0d644c3c95e35bf";
        assertEquals(expectedHashLowercase, md5.hash(inputLowercase), "MD5 hash should be case-sensitive for lowercase 'test'.");
        assertEquals(expectedHashUppercase, md5.hash(inputUppercase), "MD5 hash should be case-sensitive for uppercase 'TEST'.");
    }

    @Test
    void md5HashingWithSpecialCharacters() {
        String input = "!@#$%^&*()_+[];'./,<>?:\"{}|";
        String expectedHash = "b3ca051d7e6f4884ea079421f1f646dd";
        assertEquals(expectedHash, md5.hash(input), "MD5 hash should be consistent for special characters.");
    }

    @Test
    void md5HashingWithNullInput() {
        assertThrows(NullPointerException.class, () -> md5.hash(null), "Hashing null input should throw NullPointerException.");
    }
}
