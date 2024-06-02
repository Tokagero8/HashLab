package hashlab.benchmark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BenchmarkTest {

    @Test
    void testFactorialBaseCase() {
        assertEquals(1, callFactorial(0), "Factorial of 0 should be 1");
        assertEquals(1, callFactorial(1), "Factorial of 1 should be 1");
    }

    @Test
    void testFactorialRecursiveCase() {
        assertEquals(120, callFactorial(5), "Factorial of 5 should be 120");
        assertEquals(3628800, callFactorial(10), "Factorial of 10 should be 3628800");
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
