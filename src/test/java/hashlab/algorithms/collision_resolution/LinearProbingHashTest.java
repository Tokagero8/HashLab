package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.MD5Hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinearProbingHashTest {

    private LinearProbingHash<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new LinearProbingHash<>(5, new MD5Hash());
    }

    @Test
    void putAndGet() {
        hashTable.put("key1", 1);
        assertEquals(Integer.valueOf(1), hashTable.get("key1"), "Value for 'key1' should be equal 1.");
    }

    @Test
    void handleCollisions() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        assertEquals(Integer.valueOf(1), hashTable.get("key1"), "Value for 'key1' should be equal 1.");
        assertEquals(Integer.valueOf(2), hashTable.get("key2"), "Value for 'key2' should be equal 2.");
        assertEquals(Integer.valueOf(3), hashTable.get("key3"), "Value for 'key3' should be equal 3.");
    }

    @Test
    void updateValue() {
        hashTable.put("key1", 1);
        hashTable.put("key1", 2);
        assertEquals(Integer.valueOf(2), hashTable.get("key1"), "Value for 'key1' should be updated to 2.");
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
        hashTable.delete("nonexistent");
    }

    @Test
    void reset() {
        hashTable.put("key1", 1);
        hashTable.reset();
        assertNull(hashTable.get("key1"), "After reset, the value for 'key1' should be null.");
    }

    @Test
    void fullTableBehavior() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);
        hashTable.put("key4", 4);
        hashTable.put("key5", 5);
        Exception exception = assertThrows(RuntimeException.class, () -> hashTable.put("key6", 6), "Adding an item to the full table should result in an exception.");
        assertTrue(exception.getMessage().contains("The table is full. Unable to add new item: " + "key6"), "The exception should report the full table.");
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
