package hashlab.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    /*
    public static String generateUniformString(int length){
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }*/

    public static double generateUniformValue(double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double[] generateUniformValues(double min, double max, int amount) {
        double[] values = new double[amount];
        for (int i = 0; i < amount; i++) {
            values[i] = ThreadLocalRandom.current().nextDouble(min, max);
        }
        return values;
    }

    public static double generateGaussianValue(double mean, double deviation){
        return ThreadLocalRandom.current().nextGaussian() * deviation + mean;
    }

    public static double[] generateGaussianValues(double mean, double deviation, int amount) {
        double[] values = new double[amount];
        for (int i = 0; i < amount; i++) {
            values[i] = ThreadLocalRandom.current().nextGaussian() * deviation + mean;
        }
        return values;
    }

    public static double generateExponentialValue(double lambda){
        return Math.log(1 - ThreadLocalRandom.current().nextDouble()) / (-lambda);
    }

    public static double[] generateExponentialValues(double lambda, int amount) {
        double[] values = new double[amount];
        for (int i = 0; i < amount; i++) {
            values[i] = Math.log(1 - ThreadLocalRandom.current().nextDouble()) / (-lambda);
        }
        return values;
    }
}
