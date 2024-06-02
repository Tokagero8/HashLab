package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.HashFunction;

public class QuadraticProbingHash<Key, Value> implements HashAlgorithm<Key, Value> {
    private int hashTableSize;
    private int size;
    private Key[] keys;
    private Value[] values;
    private HashFunction hashFunction;

    public QuadraticProbingHash(int capacity, HashFunction function) {
        this.hashTableSize = capacity;
        this.hashFunction = function;
        keys = (Key[]) new Object[hashTableSize];
        values = (Value[]) new Object[hashTableSize];
    }

    private int hash(Key key) {
        return (hashFunction.hash(key.toString()).hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value value) {
        if (size == hashTableSize) {
            throw new RuntimeException("Hash table is full. Unable to insert new item: " + key.toString());
        }

        int offset = 1;
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
            i = (i + offset * offset) % hashTableSize;
            offset++;
        }
        keys[i] = key;
        values[i] = value;
        size++;
    }

    public Value get(Key key) {
        int offset = 1;
        for (int i = hash(key); keys[i] != null; i = (i + offset * offset) % hashTableSize) {
            if (keys[i].equals(key)) {
                return values[i];
            }
            offset++;
        }
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) {
            return;
        }

        int offset = 1;
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + offset * offset) % hashTableSize;
            offset++;
        }

        keys[i] = null;
        values[i] = null;
        size--;

        offset = 1;
        i = (i + offset * offset) % hashTableSize;
        while (keys[i] != null) {
            Key rehashKey = keys[i];
            Value rehashValue = values[i];
            keys[i] = null;
            values[i] = null;
            size--;
            put(rehashKey, rehashValue);
            offset++;
            i = (i + offset * offset) % hashTableSize;
        }
    }

    @Override
    public HashFunction getHashFunction() {
        return hashFunction;
    }

    private boolean contains(Key key) {
        return get(key) != null;
    }

    public void reset() {
        keys = (Key[]) new Object[hashTableSize];
        values = (Value[]) new Object[hashTableSize];
        size = 0;
    }
}
