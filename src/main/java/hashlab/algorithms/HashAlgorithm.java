package hashlab.algorithms;

import hashlab.functions.HashFunction;

public interface HashAlgorithm<Key, Value> {

    void put(Key key, Value value);
    Value get(Key key);
    void delete(Key key);
    HashFunction getHashFunction();
}
