package hashlab.tests;

import java.util.List;
import java.util.Map;

public class HashTestConfig {
    private String id;
    private String testName;
    private String algorithm;
    private int hashTableSize;
    private int chunkSize;
    private List<String> hashFunctions;
    private boolean isPutSelected, isGetSelected, isDeleteSelected;
    private boolean isDataGenerated;
    private boolean isGeneratedOnAdd;
    private String selectedFilePath;
    private boolean isLoadedOnAdd;
    private int dataSize;
    private boolean isUniformSelected, isGaussianSelected, isExponentialSelected;
    private double min, max, mean, deviation, lambda;
    private String uniformDataString;
    private String gaussianDataString;
    private String exponentialDataString;
    private String loadedDataString;
    private int benchmarkIterations;
    private double benchmarkThreshold;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getHashTableSize() {
        return hashTableSize;
    }

    public void setHashTableSize(int hashTableSize) {
        this.hashTableSize = hashTableSize;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public List<String> getHashFunctions() {
        return hashFunctions;
    }

    public void setHashFunctions(List<String> hashFunctions) {
        this.hashFunctions = hashFunctions;
    }

    public boolean isPutSelected() {
        return isPutSelected;
    }

    public void setPutSelected(boolean putSelected) {
        this.isPutSelected = putSelected;
    }

    public boolean isGetSelected() {
        return isGetSelected;
    }

    public void setGetSelected(boolean getSelected) {
        this.isGetSelected = getSelected;
    }

    public boolean isDeleteSelected() {
        return isDeleteSelected;
    }

    public void setDeleteSelected(boolean deleteSelected) {
        this.isDeleteSelected = deleteSelected;
    }

    public boolean isDataGenerated() {
        return isDataGenerated;
    }

    public void setDataGenerated(boolean dataGenerated) {
        isDataGenerated = dataGenerated;
    }

    public boolean isGeneratedOnAdd(){
        return isGeneratedOnAdd;
    }

    public void setGeneratedOnAdd(boolean generatedOnAdd){
        isGeneratedOnAdd = generatedOnAdd;
    }

    public String getSelectedFilePath(){
        return selectedFilePath;
    }

    public void setSelectedFilePath(String selectedFilePath){
        this.selectedFilePath = selectedFilePath;
    }

    public boolean isLoadedOnAdd(){
        return isLoadedOnAdd;
    }

    public void setLoadedOnAdd(boolean loadedOnAdd){
        isLoadedOnAdd = loadedOnAdd;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public boolean isUniformSelected() {
        return isUniformSelected;
    }

    public void setUniformSelected(boolean uniformSelected) {
        this.isUniformSelected = uniformSelected;
    }

    public boolean isGaussianSelected() {
        return isGaussianSelected;
    }

    public void setGaussianSelected(boolean gaussianSelected) {
        this.isGaussianSelected = gaussianSelected;
    }

    public boolean isExponentialSelected() {
        return isExponentialSelected;
    }

    public void setExponentialSelected(boolean exponentialSelected) {
        this.isExponentialSelected = exponentialSelected;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public String getUniformDataString(){
        return uniformDataString;
    }

    public void setUniformDataString(String uniformDataString){
        this.uniformDataString = uniformDataString;
    }

    public String getGaussianDataString(){
        return gaussianDataString;
    }

    public void setGaussianDataString(String gaussianDataString){
        this.gaussianDataString = gaussianDataString;
    }

    public String getExponentialDataString(){
        return exponentialDataString;
    }

    public void setExponentialDataString(String exponentialDataString){
        this.exponentialDataString = exponentialDataString;
    }

    public String getLoadedDataString(){
        return loadedDataString;
    }

    public void setLoadedDataString(String loadedDataString){
        this.loadedDataString = loadedDataString;
    }

    public int getBenchmarkIterations() {
        return benchmarkIterations;
    }

    public void setBenchmarkIterations(int benchmarkIterations) {
        this.benchmarkIterations = benchmarkIterations;
    }

    public double getBenchmarkThreshold() {
        return benchmarkThreshold;
    }

    public void setBenchmarkThreshold(double benchmarkThreshold) {
        this.benchmarkThreshold = benchmarkThreshold;
    }

    @Override
    public String toString() {
        return  "Id: " + id + "\n" +
                "Test name: " + testName + "\n" +
                "Algorithm: " + algorithm + "\n" +
                "Hash Table Size: " + hashTableSize + "\n" +
                "Chunk Size: " + chunkSize + "\n" +
                "Hash functions: " + hashFunctions + "\n" +
                "Operations: " + getOperationsString() + "\n" +
                "Data type: " + getDataType() + "\n" +
                "Data Size: " + (isDataGenerated ? dataSize : "N/A") + "\n" +
                "Data generation methods: " + getDataGenerationMethodsString() + "\n" +
                "Data generation parameters: " + getDataGenerationParamsString() + "\n" +
                "Number of benchmark iterations: " + benchmarkIterations + "\n" +
                "Benchmark threshold: " + benchmarkThreshold;
    }

    private String getDataType(){
        final String GENERATED_ON_ADD = "Generated on a test add";
        final String GENERATED_DURING_TEST = "Generated during a test";
        final String LOADED_ON_ADD = "Load from file on a test add";
        final String LOADED_DURING_TEST = "Load from file during a test";
        if (isDataGenerated) {
            return isGeneratedOnAdd ? GENERATED_ON_ADD : GENERATED_DURING_TEST;
        } else {
            return isLoadedOnAdd ? LOADED_ON_ADD : LOADED_DURING_TEST;
        }
    }

    private String getOperationsString() {
        String operations = "";
        if (isPutSelected) operations += "Put ";
        if (isGetSelected) operations += "Get ";
        if (isDeleteSelected) operations += "Delete";
        return operations.trim();
    }


    private String getDataGenerationMethodsString() {
        String methods = "";
        if (isUniformSelected) methods += "Uniform ";
        if (isGaussianSelected) methods += "Gaussian ";
        if (isExponentialSelected) methods += "Exponential";
        return methods.trim();
    }

    private String getDataGenerationParamsString() {
        if (!isDataGenerated) return "N/A";
        String params = "";
        if (isUniformSelected) params += "Min: " + min + ", Max: " + max + "; ";
        if (isGaussianSelected) params += "Mean: " + mean + ", Deviation: " + deviation + "; ";
        if (isExponentialSelected) params += "Lambda: " + lambda;
        return params.trim();
    }
}
