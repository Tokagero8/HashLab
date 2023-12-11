package hashlab.algorithms;

import hashlab.functions.HashFunction;

public class LinearProbingHash<Key, Value> implements HashAlgorithm<Key, Value> {
    private int hashTableSize;
    private int size;
    private Key[] keys;
    private Value[] values;
    private HashFunction hashFunction;

    public LinearProbingHash(int capacity, HashFunction function) {
        this.hashTableSize = capacity;
        this.hashFunction = function;
        keys = (Key[]) new Object[hashTableSize];
        values = (Value[]) new Object[hashTableSize];
    }

    private int hash(Key key) {
        return (hashFunction.hash(key.toString()).hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % hashTableSize) {
            if (keys[i].equals(key)) {
                values[i] = val;
                return;
            }
        }
        keys[i] = key;
        values[i] = val;
        size++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % hashTableSize)
            if (keys[i].equals(key))
                return values[i];
        return null;
    }
}
