package hashlab.algorithms.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MD5HashTest {

    @Test
    void md5HashingConsistency(){
        HashFunction md5 = new MD5Hash();
        String input = "test";
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(expectedHash, md5.hash(input));
    }

    @Test
    void md5HashingWithEmptyString() {
        HashFunction md5 = new MD5Hash();
        String input = "";
        String expectedHash = "d41d8cd98f00b204e9800998ecf8427e";
        assertEquals(expectedHash, md5.hash(input));
    }

    @Test
    void md5HashingWithLongString() {
        HashFunction md5 = new MD5Hash();
        String input = "This is a longer string to test the MD5 hashing function.";
        String expectedHash = "a44871c7d14322299172d7a1b2b0fd33";
        assertEquals(expectedHash, md5.hash(input));
    }

    @Test
    void md5HashingCaseSensitivity() {
        HashFunction md5 = new MD5Hash();
        String inputLowercase = "test";
        String inputUppercase = "TEST";
        String expectedHashLowercase = "098f6bcd4621d373cade4e832627b4f6";
        String expectedHashUppercase = "033bd94b1168d7e4f0d644c3c95e35bf";
        assertEquals(expectedHashLowercase, md5.hash(inputLowercase));
        assertEquals(expectedHashUppercase, md5.hash(inputUppercase));
    }

    @Test
    void md5HashingWithSpecialCharacters() {
        HashFunction md5 = new MD5Hash();
        String input = "!@#$%^&*()_+[];'./,<>?:\"{}|";
        String expectedHash = "b3ca051d7e6f4884ea079421f1f646dd";
        assertEquals(expectedHash, md5.hash(input));
    }

    @Test
    void md5HashingWithNullInput() {
        HashFunction md5 = new MD5Hash();
        assertThrows(NullPointerException.class, () -> {
            md5.hash(null);
        });
    }
}
