package hashlab.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    public String generateUniformString(int length){
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public double generateUniformValue(double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public double generateGaussianValue(double mean, double deviation){
        return ThreadLocalRandom.current().nextGaussian() * deviation + mean;
    }

    public double generateExponentialValue(double lambda){
        return Math.log(1 - ThreadLocalRandom.current().nextDouble()) / (-lambda);
    }
}
