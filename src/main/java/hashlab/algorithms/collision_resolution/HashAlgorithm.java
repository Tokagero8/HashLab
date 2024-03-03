package hashlab.algorithms.collision_resolution;

import hashlab.algorithms.hash.HashFunction;

public interface HashAlgorithm<Key, Value> {

    void put(Key key, Value value);
    Value get(Key key);
    void delete(Key key);
    HashFunction getHashFunction();
    void reset();
}
