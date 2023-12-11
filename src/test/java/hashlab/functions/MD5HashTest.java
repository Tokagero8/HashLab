package hashlab.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MD5HashTest {

    @Test
    void md5HasingConsistency(){
        HashFunction md5 = new MD5Hash();
        String input = "test";
        String expedtedHash = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(expedtedHash, md5.hash(input));
    }
}
