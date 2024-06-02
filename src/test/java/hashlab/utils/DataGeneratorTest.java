package hashlab.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataGeneratorTest {

    @Test
    void testUniformASCIIValueLength() {
        int expectedLength = 100;
        String result = DataGenerator.generateUniformASCIIValue(expectedLength);
        assertEquals(expectedLength, result.length(), "Uniform value should have correct length.");
    }

    @Test
    void testGaussianASCIIValueLength() {
        int expectedLength = 100;
        String result = DataGenerator.generateGaussianASCIIValue(64, 20, expectedLength);
        assertEquals(expectedLength, result.length(), "Gaussian value should have correct length.");
    }

    @Test
    void testExponentialASCIIValueLength() {
        int expectedLength = 100;
        String result = DataGenerator.generateExponentialASCIIValue(0.5, expectedLength);
        assertEquals(expectedLength, result.length(), "Exponential value should have correct length.");
    }

    @Test
    void testUniformASCIIValueWithAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['A'] = true;
        String result = DataGenerator.generateUniformASCIIValue(allowedChars, 50);
        assertTrue(result.matches("A+"), "Generated string should only contain 'A'.");
    }

    @Test
    void testGaussianASCIIValueWithAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['B'] = true;
        String result = DataGenerator.generateGaussianASCIIValue(allowedChars, 64, 20, 50);
        assertTrue(result.matches("B+"), "Generated string should only contain 'B'.");
    }

    @Test
    void testExponentialASCIIValueWithAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['C'] = true;
        String result = DataGenerator.generateExponentialASCIIValue(allowedChars, 0.5, 50);
        assertTrue(result.matches("C+"), "Generated string should only contain 'C'.");
    }

    @Test
    void testUniformASCIIValueWithMixedAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['A'] = true;
        allowedChars['B'] = true;
        String result = DataGenerator.generateUniformASCIIValue(allowedChars, 50);
        assertTrue(result.matches("[AB]+"), "Generated string should only contain 'A' and 'B'.");
    }

    @Test
    void testGaussianASCIIValueWithMixedAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['A'] = true;
        allowedChars['B'] = true;
        String result = DataGenerator.generateGaussianASCIIValue(allowedChars, 64, 20, 50);
        assertTrue(result.matches("[AB]+"), "Generated string should only contain 'A' and 'B'.");
    }

    @Test
    void testExponentialASCIIValueWithMixedAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        allowedChars['A'] = true;
        allowedChars['B'] = true;
        String result = DataGenerator.generateExponentialASCIIValue(allowedChars, 0.5, 50);
        assertTrue(result.matches("[AB]+"), "Generated string should only contain 'A' and 'B'.");
    }

    @Test
    void testUniformASCIIValueWithAllAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        String result = DataGenerator.generateUniformASCIIValue(allowedChars, 50);
        assertEquals(50, result.length(), "Generated string should have the correct length.");
    }

    @Test
    void testGaussianASCIIValueWithAllAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        String result = DataGenerator.generateGaussianASCIIValue(allowedChars, 64, 20, 50);
        assertEquals(50, result.length(), "Generated string should have the correct length.");
    }

    @Test
    void testExponentialASCIIValueWithAllAllowedChars() {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        String result = DataGenerator.generateExponentialASCIIValue(allowedChars, 0.5, 50);
        assertEquals(50, result.length(), "Generated string should have the correct length.");
    }

}
