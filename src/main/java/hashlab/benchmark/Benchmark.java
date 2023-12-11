package hashlab.benchmark;

public class Benchmark {

    private static long factorial(int n){
        return (n <= 1) ? 1 : n * factorial(n - 1);
    }

    public static double calculateBaseline(int iterations, double threshold){
        double previousAverage = 0;
        double total = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            factorial(20);
            long endTime = System.nanoTime();

            total += (endTime - startTime);
            double currentAverage = total / (i + 1);

            if (i > 0 && Math.abs(currentAverage - previousAverage) < threshold){
                return currentAverage;
            }

            previousAverage = currentAverage;
        }

        return previousAverage;
    }
}
