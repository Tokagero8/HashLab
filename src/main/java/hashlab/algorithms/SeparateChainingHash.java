package hashlab.algorithms;

import hashlab.functions.HashFunction;

import java.util.LinkedList;

public class SeparateChainingHash <Key, Value> {
    private int hashTableSize;
    private LinkedList<Entry>[] hashTable;
    private HashFunction hashFunction;

    private static class Entry {
        private final Object key;
        private Object value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public SeparateChainingHash(int hashTableSize, HashFunction hashFunction) {
        this.hashTableSize = hashTableSize;
        this.hashFunction = hashFunction;
        hashTable = (LinkedList<Entry>[]) new LinkedList[hashTableSize];
        for (int i = 0; i < hashTableSize; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    private int hash(Key key) {
        return (hashFunction.hash(key.toString()).hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value value) {
        int i = hash(key);
        for (Entry entry : hashTable[i]) {
            if (key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }
        hashTable[i].add(new Entry(key, value));
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Entry entry : hashTable[i]) {
            if (key.equals(entry.key)) {
                return (Value) entry.value;
            }
        }
        return null;
    }

}
