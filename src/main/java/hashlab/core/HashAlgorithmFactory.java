package hashlab.core;

import hashlab.algorithms.collision_resolution.BSTHash;
import hashlab.algorithms.collision_resolution.HashAlgorithm;
import hashlab.algorithms.collision_resolution.LinearProbingHash;
import hashlab.algorithms.collision_resolution.SeparateChainingHash;
import hashlab.algorithms.hash.HashFunction;
import hashlab.algorithms.hash.MD5Hash;
import hashlab.algorithms.hash.SHA1Hash;
import hashlab.algorithms.hash.SHA256Hash;

public class HashAlgorithmFactory {

    public static HashAlgorithm<String, Integer> createAlgorithm(String algorithmType, String hashFunctionType, int size) {
        HashFunction hashFunction = createHashFunction(hashFunctionType);
        switch (algorithmType) {
            case "BST":
                return new BSTHash<>(size, hashFunction);
            case "Linear Probing":
                return new LinearProbingHash<>(size, hashFunction);
            case "Separate Chaining":
                return new SeparateChainingHash<>(size, hashFunction);
            default:
                throw new IllegalArgumentException("Nieznany typ algorytmu: " + algorithmType);
        }
    }

    private static HashFunction createHashFunction(String hashFunctionType) {
        switch (hashFunctionType) {
            case "MD5":
                return new MD5Hash();
            case "SHA1":
                return new SHA1Hash();
            case "SHA256":
                return new SHA256Hash();
            default:
                throw new IllegalArgumentException("Nieznany typ funkcji haszującej: " + hashFunctionType);
        }
    }
}
