package hashlab.core;

import hashlab.algorithms.HashAlgorithm;
import hashlab.functions.HashFunction;

public class HashManager<Key, Value> {

    private HashAlgorithm<Key, Value> currentHashAlgorithm;
    private HashFunction currentHashFunction;

    public void setHashAlgorithm(HashAlgorithm<Key, Value> algorithm){
        this.currentHashAlgorithm = algorithm;
    }

    public void setHashFunction(HashFunction function){
        this.currentHashFunction = function;
    }

}
