package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.collision_resolution.SeparateChainingHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeparateChainingHashTest {

    private hashlab.algorithms.collision_resolution.SeparateChainingHash<String, Integer> SeparateChainingHash;

    @BeforeEach
    void setUp(){
        SeparateChainingHash = new SeparateChainingHash<>(10, key -> Integer.toString(key.hashCode()));
    }

    @Test
    void putAndGet(){
        SeparateChainingHash.put("key1", 100);
        SeparateChainingHash.put("key2", 200);

        assertEquals(100, SeparateChainingHash.get("key1"));
        assertEquals(100, SeparateChainingHash.get("key1"));
        assertNull(SeparateChainingHash.get("key3"));
    }

    @Test
    void updateValue(){
        SeparateChainingHash.put("key1", 100);
        SeparateChainingHash.put("key1", 200);

        assertEquals(200, SeparateChainingHash.get("key1"));
    }

    @Test
    void deleteValue(){
        SeparateChainingHash.put("key1", 100);
        SeparateChainingHash.put("key2", 200);

        assertEquals(100, SeparateChainingHash.get("key1"));
        assertEquals(200, SeparateChainingHash.get("key2"));

        SeparateChainingHash.delete("key1");
        assertNull(SeparateChainingHash.get("key1"));
        assertEquals(200, SeparateChainingHash.get("key2"));
    }
}