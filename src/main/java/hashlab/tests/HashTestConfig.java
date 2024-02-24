package hashlab.tests;

import java.util.List;
import java.util.Map;

public class HashTestConfig {
    String id;
    String testName;
    String algorithm;
    int hashTableSize;
    int chunkSize;
    List<String> hashFunctions;
    boolean put, get, delete;
    boolean isDataGenerated;
    boolean isGeneratedOnAdd;
    String selectedFilePath;
    boolean isLoadedOnAdd;
    int dataSize;
    boolean uniformSelected, gaussianSelected, exponentialSelected;
    double min, max, mean, deviation, lambda;
    String uniformDataString;
    String gaussianDataString;
    String exponentialDataString;
    List<Map.Entry<String, String[]>> loadedDataString;
    int benchmarkIterations;
    double benchmarkThreshold;

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

    public boolean isPut() {
        return put;
    }

    public void setPut(boolean put) {
        this.put = put;
    }

    public boolean isGet() {
        return get;
    }

    public void setGet(boolean get) {
        this.get = get;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
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
        return uniformSelected;
    }

    public void setUniformSelected(boolean uniformSelected) {
        this.uniformSelected = uniformSelected;
    }

    public boolean isGaussianSelected() {
        return gaussianSelected;
    }

    public void setGaussianSelected(boolean gaussianSelected) {
        this.gaussianSelected = gaussianSelected;
    }

    public boolean isExponentialSelected() {
        return exponentialSelected;
    }

    public void setExponentialSelected(boolean exponentialSelected) {
        this.exponentialSelected = exponentialSelected;
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

    public List<Map.Entry<String, String[]>> getLoadedDataString(){
        return loadedDataString;
    }

    public void setLoadedDataString(List<Map.Entry<String, String[]>> loadedDataString){
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
