package hashlab.benchmark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BenchmarkTest {

    @Test
    void testFactorialBaseCase() {
        assertEquals(1, callFactorial(0), "Factorial of 0 should be 1");
        assertEquals(1, callFactorial(1), "Factorial of 1 should be 1");
        assertEquals(120, callFactorial(5), "Factorial of 5 should be 120");
        assertEquals(3628800, callFactorial(10), "Factorial of 10 should be 3628800");
    }

    @Test
    void testFactorialWithNegativeNumber() {
        Exception exception = assertThrows(RuntimeException.class, () -> callFactorial(-1), "Factorial of negative number should throw RuntimeException");
        assertTrue(exception.getMessage().contains("Negative number"), "Exception message should indicate negative number");
    }

    @Test
    void testFactorialWithLargeNumber() {
        assertEquals(2432902008176640000L, callFactorial(20), "Factorial of 20 should be 2432902008176640000");
    }

    @Test
    void testCalculateBaselineWithLowIterations() {
        double baseline = Benchmark.calculateBaseline(1, 0.1);
        assertTrue(baseline > 0, "Baseline should be greater than 0 for 1 iteration");
    }

    @Test
    void testCalculateBaselineWithHighIterations() {
        double baseline = Benchmark.calculateBaseline(100, 0.1);
        assertTrue(baseline > 0, "Baseline should be greater than 0 for 100 iterations");
    }

    @Test
    void testCalculateBaselineWithThreshold() {
        double baseline = Benchmark.calculateBaseline(1000, 0.0001);
        assertTrue(baseline > 0, "Baseline should be greater than 0 for 1000 iterations with a small threshold");
    }

    @Test
    void testCalculateBaselineConvergence() {
        double threshold = 0.1;
        double baseline = Benchmark.calculateBaseline(10000, threshold);
        assertTrue(baseline > 0, "Baseline should be greater than 0");

        assertNotEquals(10000, baseline, "The calculation should converge before 10000 iterations");
    }

    @Test
    void testCalculateBaselineWithZeroIterations() {
        double baseline = Benchmark.calculateBaseline(0, 0.1);
        assertEquals(0, baseline, "Baseline should be 0 for 0 iterations");
    }

    @Test
    void testCalculateBaselineWithNegativeIterations() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Benchmark.calculateBaseline(-1, 0.1), "Negative iterations should throw IllegalArgumentException");
        assertTrue(exception.getMessage().contains("Negative iterations"), "Exception message should indicate negative iterations");
    }

    @Test
    void testCalculateBaselineWithHighThreshold() {
        double baseline = Benchmark.calculateBaseline(100, 1.0);
        assertTrue(baseline > 0, "Baseline should be greater than 0 for high threshold");
    }

    private long callFactorial(int n) {
        try {
            java.lang.reflect.Method method = Benchmark.class.getDeclaredMethod("factorial", int.class);
            method.setAccessible(true);
            return (long) method.invoke(null, n);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
