package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.HashFunction;

public class BSTHash<Key extends Comparable<Key>, Value> implements HashAlgorithm<Key, Value> {
    private Object[] hashTable;
    private int hashTableSize;
    private HashFunction hashFunction;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }


    public BSTHash(int hashTableSize, HashFunction function) {
        this.hashTableSize = hashTableSize;
        this.hashFunction = function;
        hashTable = new Object[hashTableSize];
    }

    private int hash(Key key) {
        return (hashFunction.hash(key.toString()).hashCode() & 0x7fffffff) % hashTableSize;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        hashTable[i] = put((Node) hashTable[i], key, val);
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
        return get((Node)hashTable[i], key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    public void delete(Key key) {
        int i = hash(key);
        hashTable[i] = delete((Node)hashTable[i], key);
    }

    @Override
    public HashFunction getHashFunction() {
        return hashFunction;
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        return node;
    }

    private Node min(Node node){
        if (node.left == null) return node;
        else return min(node.left);
    }

    private Node deleteMin(Node node){
        if(node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }
}
