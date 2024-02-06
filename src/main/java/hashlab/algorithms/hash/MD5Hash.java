package hashlab.algorithms.hash;

import static hashlab.utils.HashUtils.hashWithFunction;

public class MD5Hash implements HashFunction {

    @Override
    public String hash(String input) {
        return hashWithFunction("MD5", input);
    }
}
