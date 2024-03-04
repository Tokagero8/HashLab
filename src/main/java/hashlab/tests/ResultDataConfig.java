package hashlab.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultDataConfig {
    private List<TestResult> results;

    public ResultDataConfig() {
        this.results = new ArrayList<>();
    }

    public void addResult(TestResult result) {
        results.add(result);
    }

    public List<TestResult> getResults() {
        return results;
    }

    public static class TestResult {
        private String algorithm;
        private String function;
        private int tableSize;
        private String dataType;
        private int dataSize;
        private int chunkSize;
        private String operation;
        private double result;

        public TestResult(String algorithm, String function, int tableSize, String dataType, int dataSize, int chunkSize, String operation, double result) {
            this.algorithm = algorithm;
            this.function = function;
            this.tableSize = tableSize;
            this.dataType = dataType;
            this.dataSize = dataSize;
            this.chunkSize = chunkSize;
            this.operation = operation;
            this.result = result;
        }

        // Getters
        public String getAlgorithm() {
            return algorithm;
        }

        public String getFunction() {
            return function;
        }

        public int getTableSize() {
            return tableSize;
        }

        public String getDataType() {
            return dataType;
        }

        public int getDataSize() {
            return dataSize;
        }

        public int getChunkSize() {
            return chunkSize;
        }

        public String getOperation() {
            return operation;
        }

        public double getResult() {
            return result;
        }
    }

    public Set<String> getUniqueAlgorithms() {
        return results.stream().map(TestResult::getAlgorithm).collect(Collectors.toSet());
    }

    public Set<String> getUniqueFunctions() {
        return results.stream().map(TestResult::getFunction).collect(Collectors.toSet());
    }

    public Set<String> getUniqueDataTypes() {
        return results.stream().map(TestResult::getDataType).collect(Collectors.toSet());
    }

    public Set<String> getUniqueOperations() {
        return results.stream().map(TestResult::getOperation).collect(Collectors.toSet());
    }
}
