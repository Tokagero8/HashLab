package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.collision_resolution.BSTHash;
import hashlab.algorithms.hash.MD5Hash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BSTHashTest {

    private BSTHash<String, Integer> bstHash;

    @BeforeEach
    void setUp() {
        bstHash = new BSTHash<>(10, new MD5Hash());
    }

    @Test
    void putAndGet() {
        bstHash.put("key1", 1);
        assertEquals(Integer.valueOf(1), bstHash.get("key1"), "The value for 'key1' should be 1.");
    }

    @Test
    void deleteLeafNode() {
        bstHash.put("key1", 1);
        bstHash.delete("key1");
        assertNull(bstHash.get("key1"), "After deletion, the value for 'key1' should be null.");
    }

    @Test
    void deleteInternalNode() {
        bstHash.put("key1", 1);
        bstHash.put("key2", 2);
        bstHash.put("key3", 3);
        bstHash.delete("key2");
        assertNull(bstHash.get("key2"), "After deletion, the value for 'key2' should be null.");

        assertEquals(Integer.valueOf(1), bstHash.get("key1"), "The value for 'key1' should remain available.");
        assertEquals(Integer.valueOf(3), bstHash.get("key3"), "The value for 'key3' should remain available.");
    }

    @Test
    void resetHashTable() {
        bstHash.put("key1", 1);
        bstHash.reset();
        assertNull(bstHash.get("key1"), "After the reset, the hash table should be empty.");
    }

    @Test
    void handleCollisions() {
        String key1 = "key1";
        String key2 = "key2";
        bstHash.put(key1, 1);
        bstHash.put(key2, 2);
        assertEquals(Integer.valueOf(1), bstHash.get(key1), "The value for 'key1' should be 1.");
        assertEquals(Integer.valueOf(2), bstHash.get(key2), "The value for 'key2' should be 2.");
    }

    @Test
    void updateValue() {
        bstHash.put("key1", 1);
        bstHash.put("key1", 2);
        assertEquals(Integer.valueOf(2), bstHash.get("key1"), "The value for 'key1' should be updated to 2.");
    }

    @Test
    void retrieveNonExistentKey() {
        assertNull(bstHash.get("nonexistent"), "The value for a non-existent key should be null.");
    }

    @Test
    void deleteNonExistentKey() {
        bstHash.delete("nonexistent");
    }

    @Test
    void extensiveOperations() {
        bstHash.put("key1", 1);
        bstHash.put("key2", 2);
        assertEquals(Integer.valueOf(1), bstHash.get("key1"), "The value for 'key1' should be 1.");
        assertEquals(Integer.valueOf(2), bstHash.get("key2"), "The value for 'key2' should be 2.");

        bstHash.delete("key1");
        assertNull(bstHash.get("key1"), "After deletion, the value for 'key1' should be null.");

        bstHash.put("key1", 3);
        assertEquals(Integer.valueOf(3), bstHash.get("key1"), "The value for 'key1' should be 3.");

        bstHash.reset();
        assertNull(bstHash.get("key1"), "After the reset, the hash table should be empty.");
        assertNull(bstHash.get("key2"), "After the reset, the hash table should be empty.");
    }
}