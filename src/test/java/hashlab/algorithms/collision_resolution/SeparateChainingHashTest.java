package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.MD5Hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeparateChainingHashTest {

    private SeparateChainingHash<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new SeparateChainingHash<>(10, new MD5Hash());
    }

    @Test
    void putAndGet() {
        hashTable.put("key1", 1);
        assertEquals(Integer.valueOf(1), hashTable.get("key1"), "The value for 'key1' should be 1.");
    }

    @Test
    void handleCollisions() {
        String key1 = "AaAa";
        String key2 = "BBBB";
        int hash1 = key1.hashCode() & 0x7fffffff;
        int hash2 = key2.hashCode() & 0x7fffffff;

        assertEquals(hash1 % 10, hash2 % 10, "The keys should lead to collisions.");

        hashTable.put(key1, 1);
        hashTable.put(key2, 2);

        assertEquals(Integer.valueOf(1), hashTable.get(key1), "The value for 'AaAa' should be 1.");
        assertEquals(Integer.valueOf(2), hashTable.get(key2), "The value for 'BBBB' should be 2.");
    }

    @Test
    void updateValue() {
        hashTable.put("key1", 1);
        hashTable.put("key1", 2);
        assertEquals(Integer.valueOf(2), hashTable.get("key1"), "The value for 'key1' should be updated to 2.");
    }

    @Test
    void retrieveNonExistentKey() {
        assertNull(hashTable.get("nonexistent"), "The value for a non-existent key should be null.");
    }

    @Test
    void delete() {
        hashTable.put("key1", 1);
        hashTable.delete("key1");
        assertNull(hashTable.get("key1"), "After deletion, the value for 'key1' should be null.");
    }

    @Test
    void deleteNonExistentKey() {
        hashTable.delete("nonexistent"); // Should not throw any exception
    }

    @Test
    void reset() {
        hashTable.put("key1", 1);
        assertNotNull(hashTable.get("key1"));
        hashTable.reset();
        assertNull(hashTable.get("key1"), "The table should be empty after the reset.");
    }

    @Test
    void handleMultipleCollisions() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        assertEquals(Integer.valueOf(1), hashTable.get("key1"), "The value for 'key1' should be 1.");
        assertEquals(Integer.valueOf(2), hashTable.get("key2"), "The value for 'key2' should be 2.");
        assertEquals(Integer.valueOf(3), hashTable.get("key3"), "The value for 'key3' should be 3.");
    }

    @Test
    void extensiveOperations() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        assertEquals(Integer.valueOf(1), hashTable.get("key1"), "The value for 'key1' should be 1.");
        assertEquals(Integer.valueOf(2), hashTable.get("key2"), "The value for 'key2' should be 2.");

        hashTable.delete("key1");
        assertNull(hashTable.get("key1"), "After deletion, the value for 'key1' should be null.");

        hashTable.put("key1", 3);
        assertEquals(Integer.valueOf(3), hashTable.get("key1"), "The value for 'key1' should be 3.");

        hashTable.reset();
        assertNull(hashTable.get("key1"), "After the reset, the hash table should be empty.");
        assertNull(hashTable.get("key2"), "After the reset, the hash table should be empty.");
    }

}