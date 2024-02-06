package hashlab.algorithms;

import hashlab.algorithms.collision_resolution.LinearProbingHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinearProbingHashTest {

    private LinearProbingHash<String, Integer> linearProbingHash;

    @BeforeEach
    void setUp(){
        linearProbingHash = new LinearProbingHash<>(10, key -> Integer.toString(key.hashCode()));
    }

    @Test
    void putAndGet(){
        linearProbingHash.put("key1", 100);
        linearProbingHash.put("key2", 200);

        assertEquals(100, linearProbingHash.get("key1"));
        assertEquals(200, linearProbingHash.get("key2"));
        assertNull(linearProbingHash.get("key3"));
    }

    @Test
    void updateValue(){
        linearProbingHash.put("key1", 100);
        linearProbingHash.put("key1", 200);

        assertEquals(200, linearProbingHash.get("key1"));
    }

    @Test
    void deleteValue(){
        linearProbingHash.put("key1", 100);
        linearProbingHash.put("key2", 200);

        assertEquals(100, linearProbingHash.get("key1"));
        assertEquals(200, linearProbingHash.get("key2"));

        linearProbingHash.delete("key1");
        assertNull(linearProbingHash.get("key1"));
        assertEquals(200, linearProbingHash.get("key2"));
    }
}
