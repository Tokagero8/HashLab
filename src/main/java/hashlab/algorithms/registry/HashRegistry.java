package hashlab.algorithms.registry;

import hashlab.algorithms.collision_resolution.BSTHash;
import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.algorithms.collision_resolution.LinearProbingHash;
import hashlab.algorithms.collision_resolution.SeparateChainingHash;
import hashlab.algorithms.hash.HashFunction;
import hashlab.algorithms.hash.MD5Hash;
import hashlab.algorithms.hash.SHA1Hash;
import hashlab.algorithms.hash.SHA256Hash;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashRegistry {
    private static final Map<String, BiFunction<Integer, HashFunction, HashAlgorithm<String, Integer>>> algorithmCreators = new HashMap<>();
    private static final Map<String, Function<Void, HashFunction>> functionCreators = new HashMap<>();

    static {
        registerHashAlgorithm("BST", BSTHash::new);
        registerHashAlgorithm("Linear Probing", LinearProbingHash::new);
        registerHashAlgorithm("Separate Chaining", SeparateChainingHash::new);

        registerHashFunction("MD5", v -> new MD5Hash());
        registerHashFunction("SHA1",  v -> new SHA1Hash());
        registerHashFunction("SHA256", v -> new SHA256Hash());
    }

    public static void registerHashAlgorithm(String algorithm, BiFunction<Integer, HashFunction, HashAlgorithm<String, Integer>> creator) {
        algorithmCreators.put(algorithm, creator);
    }

    public static Set<String> getHashAlgorithms() {
        return algorithmCreators.keySet();
    }

    public static void registerHashFunction(String function, Function<Void, HashFunction> creator) {
        functionCreators.put(function, creator);
    }

    public static Set<String> getHashFunctions() {
        return functionCreators.keySet();
    }

    public static HashAlgorithm<String, Integer> createAlgorithm(String algorithmType, String hashFunctionType, int size) {
        if (!algorithmCreators.containsKey(algorithmType)) {
            throw new IllegalArgumentException("Unknown algorithm type: " + algorithmType);
        }
        if (!functionCreators.containsKey(hashFunctionType)) {
            throw new IllegalArgumentException("Unknown hash function type: " + hashFunctionType);
        }
        HashFunction hashFunction = functionCreators.get(hashFunctionType).apply(null);
        return algorithmCreators.get(algorithmType).apply(size, hashFunction);
    }
}
