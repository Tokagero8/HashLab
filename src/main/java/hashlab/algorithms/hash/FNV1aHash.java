package hashlab.algorithms.hash;

public class FNV1aHash implements HashFunction {

    private static final long FNV_PRIME = 0x100000001b3L;
    private static final long OFFSET_BASIS = 0xcbf29ce484222325L;

    @Override
    public String hash(String input) {
        long hash = OFFSET_BASIS;
        for (byte b : input.getBytes()){
            hash ^= b;
            hash *= FNV_PRIME;
        }
        return Long.toHexString(hash);
    }
}
