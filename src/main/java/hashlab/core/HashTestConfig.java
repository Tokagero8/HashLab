package hashlab.core;

import java.util.List;

public class HashTestConfig {
    String testName;
    String algorithm;
    List<String> hashFunctions;
    boolean put, get, delete;
    boolean isDataGenerated;
    boolean uniformSelected, gaussianSelected, exponentialSelected;
    double min, max, mean, deviation, lambda;
    int benchmarkIterations;
    double benchmarkThreshold;

    @Override
    public String toString() {
        return "Nazwa testu: " + testName + "\n" +
                "Algorytm: " + algorithm + "\n" +
                "Funkcje haszujące: " + hashFunctions + "\n" +
                "Operacje: " + getOperationsString() + "\n" +
                "Typ danych: " + (isDataGenerated ? "Generowane" : "Wczytane z pliku") + "\n" +
                "Metody generacji danych: " + getDataGenerationMethodsString() + "\n" +
                "Parametry generacji danych: " + getDataGenerationParamsString() + "\n" +
                "Liczba iteracji benchmarku: " + benchmarkIterations + "\n" +
                "Próg benchmarku: " + benchmarkThreshold;
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
