package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.collision_resolution.BSTHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BSTHashTest {

    private BSTHash<String, Integer> bstHash;

    @BeforeEach
    void setUp(){
        bstHash = new BSTHash<>(10, key -> Integer.toString(key.hashCode()));
    }

    @Test
    void putAndGet(){
        bstHash.put("key1", 100);
        bstHash.put("key2", 200);

        assertEquals(100, bstHash.get("key1"));
        assertEquals(200, bstHash.get("key2"));
        assertNull(bstHash.get("key3"));
    }

    @Test
    void updateValue(){
        bstHash.put("key1", 100);
        bstHash.put("key1", 200);

        assertEquals(200, bstHash.get("key1"));
    }

    @Test
    void deleteValue(){
        bstHash.put("key1", 100);
        bstHash.put("key2", 200);

        assertEquals(100, bstHash.get("key1"));
        assertEquals(200, bstHash.get("key2"));

        bstHash.delete("key1");
        assertNull(bstHash.get("key1"));
        assertEquals(200, bstHash.get("key2"));
    }
}