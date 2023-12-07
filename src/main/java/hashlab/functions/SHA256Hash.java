package hashlab.functions;

import static hashlab.utils.HashUtils.hashWithFunction;

public class SHA256Hash implements HashFunction {
    @Override
    public String hash(String input) {
        return hashWithFunction("SHA-256", input);
    }
}
