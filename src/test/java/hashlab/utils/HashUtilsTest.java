package hashlab.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashUtilsTest {

    @Test
    void testHashWithFunctionMD5() {
        String input = "test";
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
        String actualHash = HashUtils.hashWithFunction("MD5", input);
        assertEquals(expectedHash, actualHash, "MD5 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionSHA1() {
        String input = "test";
        String expectedHash = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        String actualHash = HashUtils.hashWithFunction("SHA-1", input);
        assertEquals(expectedHash, actualHash, "SHA-1 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionSHA256() {
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String actualHash = HashUtils.hashWithFunction("SHA-256", input);
        assertEquals(expectedHash, actualHash, "SHA-256 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionNoSuchAlgorithm() {
        String input = "test";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> HashUtils.hashWithFunction("INVALID", input), "Invalid algorithm should throw RuntimeException.");
        assertTrue(exception.getMessage().contains("Algorithm not found: INVALID"));
    }

    @Test
    void testHashWithFunctionEmptyString() {
        String input = "";
        String expectedMD5Hash = "d41d8cd98f00b204e9800998ecf8427e";
        String expectedSHA1Hash = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String expectedSHA256Hash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        String actualMD5Hash = HashUtils.hashWithFunction("MD5", input);
        String actualSHA1Hash = HashUtils.hashWithFunction("SHA-1", input);
        String actualSHA256Hash = HashUtils.hashWithFunction("SHA-256", input);

        assertEquals(expectedMD5Hash, actualMD5Hash, "MD5 hash of empty string should match the expected value.");
        assertEquals(expectedSHA1Hash, actualSHA1Hash, "SHA-1 hash of empty string should match the expected value.");
        assertEquals(expectedSHA256Hash, actualSHA256Hash, "SHA-256 hash of empty string should match the expected value.");
    }
}
