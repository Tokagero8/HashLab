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
    void testHashWithFunctionSHA224() {
        String input = "test";
        String expectedHash = "90a3ed9e32b2aaf4c61c410eb925426119e1a9dc53d4286ade99a809";
        String actualHash = HashUtils.hashWithFunction("SHA-224", input);
        assertEquals(expectedHash, actualHash, "SHA-224 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionSHA256() {
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String actualHash = HashUtils.hashWithFunction("SHA-256", input);
        assertEquals(expectedHash, actualHash, "SHA-256 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionSHA384() {
        String input = "test";
        String expectedHash = "768412320f7b0aa5812fce428dc4706b3cae50e02a64caa16a782249bfe8efc4b7ef1ccb126255d196047dfedf17a0a9";
        String actualHash = HashUtils.hashWithFunction("SHA-384", input);
        assertEquals(expectedHash, actualHash, "SHA-384 hash should match the expected value.");
    }

    @Test
    void testHashWithFunctionSHA512() {
        String input = "test";
        String expectedHash = "ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff";
        String actualHash = HashUtils.hashWithFunction("SHA-512", input);
        assertEquals(expectedHash, actualHash, "SHA-512 hash should match the expected value.");
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
        String expectedSHA224Hash = "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f";
        String expectedSHA256Hash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String expectedSHA384Hash = "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b";
        String expectedSHA512Hash = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";

        String actualMD5Hash = HashUtils.hashWithFunction("MD5", input);
        String actualSHA1Hash = HashUtils.hashWithFunction("SHA-1", input);
        String actualSHA224Hash = HashUtils.hashWithFunction("SHA-224", input);
        String actualSHA256Hash = HashUtils.hashWithFunction("SHA-256", input);
        String actualSHA384Hash = HashUtils.hashWithFunction("SHA-384", input);
        String actualSHA512Hash = HashUtils.hashWithFunction("SHA-512", input);

        assertEquals(expectedMD5Hash, actualMD5Hash, "MD5 hash of empty string should match the expected value.");
        assertEquals(expectedSHA1Hash, actualSHA1Hash, "SHA-1 hash of empty string should match the expected value.");
        assertEquals(expectedSHA224Hash, actualSHA224Hash, "SHA-224 hash of empty string should match the expected value.");
        assertEquals(expectedSHA256Hash, actualSHA256Hash, "SHA-256 hash of empty string should match the expected value.");
        assertEquals(expectedSHA384Hash, actualSHA384Hash, "SHA-384 hash of empty string should match the expected value.");
        assertEquals(expectedSHA512Hash, actualSHA512Hash, "SHA-512 hash of empty string should match the expected value.");
    }
}
