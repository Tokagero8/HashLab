package hashlab.algorithms;

public interface HashAlgorithm<Key, Value> {

    void put(Key key, Value value);
    Value get(Key key);
    void delete(Key key);
}
