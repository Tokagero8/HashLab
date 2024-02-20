package hashlab.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class DataGeneratorTest {

    @Test
    void testGenerateGaussainASCIIValue_Distribution(){
        boolean[] allowedChars = new boolean[128];
        java.util.Arrays.fill(allowedChars, false);

        for (int i = '0'; i <= 'z'; i++) {
            allowedChars[i] = true;
        }

        int amount = 10000;
        double mean = 64.0;
        double deviation = 20.0;

        String generated = DataGenerator.generateGaussianASCIIValue(allowedChars, mean, deviation, amount);

        Map<Character, Integer> histogram = new HashMap<>();
        for (char c : generated.toCharArray()) {
            histogram.put(c, histogram.getOrDefault(c, 0) + 1);
        }
    }

}
