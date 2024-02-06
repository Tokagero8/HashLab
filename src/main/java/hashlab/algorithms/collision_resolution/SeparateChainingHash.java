package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.HashFunction;

import java.util.LinkedList;

public class SeparateChainingHash <Key, Value> implements HashAlgorithm<Key, Value> {
    private int hashTableSize;
    private LinkedList<Entry<Key, Value>>[] hashTable;
    private HashFunction hashFunction;

    private static class Entry<Key, Value> {
        private final Key key;
        private Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public SeparateChainingHash(int hashTableSize, HashFunction hashFunction) {
        this.hashTableSize = hashTableSize;
        this.hashFunction = hashFunction;
        hashTable = (LinkedList<Entry<Key, Value>>[]) new LinkedList[hashTableSize];
        for (int i = 0; i < hashTableSize; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    private int hash(Key key) {
        return (hashFunction.hash(key.toString()).hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value value) {
        int i = hash(key);
        for (Entry<Key, Value> entry : hashTable[i]) {
            if (key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }
        hashTable[i].add(new Entry<>(key, value));
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Entry<Key, Value> entry : hashTable[i]) {
            if (key.equals(entry.key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void delete(Key key){
        int i = hash(key);
        LinkedList<Entry<Key, Value>> chain = hashTable[i];

        for (int j = 0; j < chain.size(); j++) {
            Entry<Key, Value> entry = chain.get(j);
            if(key.equals(entry.key)) {
                chain.remove(j);
                return;
            }
        }
    }

    @Override
    public HashFunction getHashFunction() {
        return hashFunction;
    }
}
