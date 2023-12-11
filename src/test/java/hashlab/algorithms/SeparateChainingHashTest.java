package hashlab.algorithms;

import hashlab.functions.HashFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeparateChainingHashTest {

    private SeparateChainingHash<String, Integer> bstHash;
    private HashFunction mockHashFunction;

    @BeforeEach
    void setUp(){
        mockHashFunction = key -> Integer.toString(key.hashCode());
        bstHash = new SeparateChainingHash<>(10, mockHashFunction);
    }

    @Test
    void putAndGet(){
        bstHash.put("key1", 100);
        bstHash.put("key2", 200);

        assertEquals(100, bstHash.get("key1"));
        assertEquals(100, bstHash.get("key1"));
        assertNull(bstHash.get("key3"));
    }

    @Test
    void updateValue(){
        bstHash.put("key1", 100);
        bstHash.put("key1", 200);

        assertEquals(200, bstHash.get("key1"));
    }
}
