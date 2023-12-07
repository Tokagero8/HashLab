package hashlab.algorithms;

public class BSTHash<Key extends Comparable<Key>, Value> {
    private Node[] hashTable;
    private int hashTableSize;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTHash(int hashTableSize) {
        this.hashTableSize = hashTableSize;
        hashTable = (Node[]) new Object[hashTableSize];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        hashTable[i] = put(hashTable[i], key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        return x;
    }

    public Value get(Key key) {
        int i = hash(key);
        return get(hashTable[i], key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }
}
