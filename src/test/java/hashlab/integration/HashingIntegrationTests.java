package hashlab.integration;

import hashlab.algorithms.collision_resolution.BSTHash;
import hashlab.algorithms.collision_resolution.LinearProbingHash;
import hashlab.algorithms.collision_resolution.SeparateChainingHash;
import hashlab.algorithms.hash.MD5Hash;
import hashlab.algorithms.hash.SHA1Hash;
import hashlab.algorithms.hash.SHA256Hash;
import hashlab.utils.DataGenerator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashingIntegrationTests {

    private final DataGenerator dataGenerator = new DataGenerator();

    @Test
    public void testBSTWithMD5(){
        BSTHash<String, Integer> hash = new BSTHash<>(10, new MD5Hash());
        testHasingAlgorithmBST(hash);
    }

    @Test
    public void testBSTWithSHA1(){
        BSTHash<String, Integer> hash = new BSTHash<>(10, new SHA1Hash());
        testHasingAlgorithmBST(hash);
    }

    @Test
    public void testBSTWithSHA256(){
        BSTHash<String, Integer> hash = new BSTHash<>(10, new SHA256Hash());
        testHasingAlgorithmBST(hash);
    }

    private void testHasingAlgorithmBST(BSTHash<String, Integer> hashAlgorithm){
        Double key = dataGenerator.generateUniformValue(1, 10);
        int value = 123;

        hashAlgorithm.put(key.toString(), value);
        assertEquals(value, hashAlgorithm.get(key.toString()));
    }

    @Test
    public void testLinearProbingWithMD5(){
        LinearProbingHash<String, Integer> hash = new LinearProbingHash<>(10, new MD5Hash());
        testHasingAlgorithmLinearProbing(hash);
    }

    @Test
    public void testLinearProbingWithSHA1(){
        LinearProbingHash<String, Integer> hash = new LinearProbingHash<>(10, new SHA1Hash());
        testHasingAlgorithmLinearProbing(hash);
    }

    @Test
    public void testLinearProbingWithSHA256(){
        LinearProbingHash<String, Integer> hash = new LinearProbingHash<>(10, new SHA256Hash());
        testHasingAlgorithmLinearProbing(hash);
    }

    private void testHasingAlgorithmLinearProbing(LinearProbingHash<String, Integer> hashAlgorithm){
        Double key = dataGenerator.generateUniformValue(1, 10);
        int value = 123;

        hashAlgorithm.put(key.toString(), value);
        assertEquals(value, hashAlgorithm.get(key.toString()));
    }

    @Test
    public void testSeparateChainingWithMD5(){
        SeparateChainingHash<String, Integer> hash = new SeparateChainingHash<>(10, new MD5Hash());
        testHasingAlgorithmSeparateChaining(hash);
    }

    @Test
    public void testSeparateChainingWithSHA1(){
        SeparateChainingHash<String, Integer> hash = new SeparateChainingHash<>(10, new SHA1Hash());
        testHasingAlgorithmSeparateChaining(hash);
    }

    @Test
    public void testSeparateChainingWithSHA256(){
        SeparateChainingHash<String, Integer> hash = new SeparateChainingHash<>(10, new SHA256Hash());
        testHasingAlgorithmSeparateChaining(hash);
    }

    private void testHasingAlgorithmSeparateChaining(SeparateChainingHash<String, Integer> hashAlgorithm){
        Double key = dataGenerator.generateUniformValue(1, 10);
        int value = 123;

        hashAlgorithm.put(key.toString(), value);
        assertEquals(value, hashAlgorithm.get(key.toString()));
    }

}
