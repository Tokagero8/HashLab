package hashlab.core;

import java.util.List;

public class HashTestConfig {
    String testName;
    String algorithm;
    int hashTableSize;
    List<String> hashFunctions;
    boolean put, get, delete;
    boolean isDataGenerated;
    int dataSize;
    boolean uniformSelected, gaussianSelected, exponentialSelected;
    double min, max, mean, deviation, lambda;
    int benchmarkIterations;
    double benchmarkThreshold;

    @Override
    public String toString() {
        return "Test name: " + testName + "\n" +
                "Algorithm: " + algorithm + "\n" +
                "Hash Table Size: " + hashTableSize + "\n" +
                "Hash functions: " + hashFunctions + "\n" +
                "Operations: " + getOperationsString() + "\n" +
                "Data type: " + (isDataGenerated ? "Generated" : "Loaded from a file") + "\n" +
                "Data Size: " + (isDataGenerated ? dataSize : "N/A") + "\n" +
                "Data generation methods: " + getDataGenerationMethodsString() + "\n" +
                "Data generation parameters: " + getDataGenerationParamsString() + "\n" +
                "Number of benchmark iterations: " + benchmarkIterations + "\n" +
                "Benchmark threshold: " + benchmarkThreshold;
    }

    private String getOperationsString() {
        String operations = "";
        if (put) operations += "Put ";
        if (get) operations += "Get ";
        if (delete) operations += "Delete";
        return operations.trim();
    }


    private String getDataGenerationMethodsString() {
        String methods = "";
        if (uniformSelected) methods += "Uniform ";
        if (gaussianSelected) methods += "Gaussian ";
        if (exponentialSelected) methods += "Exponential";
        return methods.trim();
    }

    private String getDataGenerationParamsString() {
        if (!isDataGenerated) return "N/A";
        String params = "";
        if (uniformSelected) params += "Min: " + min + ", Max: " + max + "; ";
        if (gaussianSelected) params += "Mean: " + mean + ", Deviation: " + deviation + "; ";
        if (exponentialSelected) params += "Lambda: " + lambda;
        return params.trim();
    }
}
