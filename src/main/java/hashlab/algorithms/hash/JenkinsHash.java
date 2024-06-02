package hashlab.algorithms.hash;

public class JenkinsHash implements HashFunction{
    @Override
    public String hash(String input) {
        int hash = 0;
        for (int i = 0; i < input.length(); i++) {
            hash += input.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);

        return Integer.toString(hash);
    }
}
