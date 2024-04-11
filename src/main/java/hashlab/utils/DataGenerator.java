package hashlab.utils;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private static char getRandomCharFromAllowed(boolean[] allowedChars){
        int randomIndex;
        do {
            randomIndex = ThreadLocalRandom.current().nextInt(128);
        } while (!allowedChars[randomIndex]);
        return (char) randomIndex;
    }

    public static String generateUniformASCIIValue(int amount) {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(getRandomCharFromAllowed(allowedChars));
        }
        return sb.toString();
    }

    public static String generateUniformASCIIValue(boolean[] allowedChars, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(getRandomCharFromAllowed(allowedChars));
        }
        return sb.toString();
    }

    public static String generateGaussianASCIIValue(double mean, double deviation, int amount) {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            int index;
            do{
                index = (int) (ThreadLocalRandom.current().nextGaussian() * deviation + mean);
                index = Math.floorMod(index, 128);
            } while (!allowedChars[index]);
            sb.append((char) index);
        }
        return sb.toString();
    }


    public static String generateGaussianASCIIValue(boolean[] allowedChars, double mean, double deviation, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            int index;
            do{
                index = (int) (ThreadLocalRandom.current().nextGaussian() * deviation + mean);
                index = Math.floorMod(index, 128);
            } while (!allowedChars[index]);
            sb.append((char) index);
        }
        return sb.toString();
    }

    public static String generateExponentialASCIIValue(double lambda, int amount) {
        boolean[] allowedChars = new boolean[128];
        Arrays.fill(allowedChars, true);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            double exponentialValue;
            int index;
            do{
                exponentialValue = Math.log(1 - ThreadLocalRandom.current().nextDouble()) / (-lambda);
                index = (int) ((exponentialValue * 127) % 128);
            } while (!allowedChars[index]);
            sb.append((char) index);
        }
        return sb.toString();
    }

    public static String generateExponentialASCIIValue(boolean[] allowedChars, double lambda, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            double exponentialValue;
            int index;
            do{
                exponentialValue = Math.log(1 - ThreadLocalRandom.current().nextDouble()) / (-lambda);
                index = (int) ((exponentialValue * 127) % 128);
            } while (!allowedChars[index]);
            sb.append((char) index);
        }
        return sb.toString();
    }
}
