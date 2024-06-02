package hashlab.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ResultDataConfigTest {
    private ResultDataConfig resultDataConfig;

    @BeforeEach
    void setUp() {
        resultDataConfig = new ResultDataConfig();
    }

    @Test
    void testAddResult() {
        ResultDataConfig.TestResult testResult = new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45);
        resultDataConfig.addResult(testResult);

        List<ResultDataConfig.TestResult> results = resultDataConfig.getResults();
        assertNotNull(results, "Results should not be null");
        assertEquals(1, results.size(), "Results size should be 1");
        assertEquals(testResult, results.get(0), "Added result should match the retrieved result");
    }

    @Test
    void testGetResults() {
        ResultDataConfig.TestResult testResult1 = new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45);
        ResultDataConfig.TestResult testResult2 = new ResultDataConfig.TestResult("Linear Probing", "SHA1", 200, "Gaussian", 2000, 20, "Get", 678.90);

        resultDataConfig.addResult(testResult1);
        resultDataConfig.addResult(testResult2);

        List<ResultDataConfig.TestResult> results = resultDataConfig.getResults();
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Results size should be 2");
        assertEquals(testResult1, results.get(0), "First result should match the first added result");
        assertEquals(testResult2, results.get(1), "Second result should match the second added result");
    }

    @Test
    void testGetUniqueAlgorithms() {
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("Linear Probing", "SHA1", 200, "Gaussian", 2000, 20, "Get", 678.90));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "SHA256", 300, "Exponential", 3000, 30, "Delete", 345.67));

        Set<String> uniqueAlgorithms = resultDataConfig.getUniqueAlgorithms();
        assertNotNull(uniqueAlgorithms, "Unique algorithms set should not be null");
        assertEquals(2, uniqueAlgorithms.size(), "Unique algorithms size should be 2");
        assertTrue(uniqueAlgorithms.contains("BST"), "Unique algorithms should contain 'BST'");
        assertTrue(uniqueAlgorithms.contains("Linear Probing"), "Unique algorithms should contain 'Linear Probing'");
    }

    @Test
    void testGetUniqueFunctions() {
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("Linear Probing", "SHA1", 200, "Gaussian", 2000, 20, "Get", 678.90));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "SHA256", 300, "Exponential", 3000, 30, "Delete", 345.67));

        Set<String> uniqueFunctions = resultDataConfig.getUniqueFunctions();
        assertNotNull(uniqueFunctions, "Unique functions set should not be null");
        assertEquals(3, uniqueFunctions.size(), "Unique functions size should be 3");
        assertTrue(uniqueFunctions.contains("MD5"), "Unique functions should contain 'MD5'");
        assertTrue(uniqueFunctions.contains("SHA1"), "Unique functions should contain 'SHA1'");
        assertTrue(uniqueFunctions.contains("SHA256"), "Unique functions should contain 'SHA256'");
    }

    @Test
    void testGetUniqueDataTypes() {
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("Linear Probing", "SHA1", 200, "Gaussian", 2000, 20, "Get", 678.90));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "SHA256", 300, "Exponential", 3000, 30, "Delete", 345.67));

        Set<String> uniqueDataTypes = resultDataConfig.getUniqueDataTypes();
        assertNotNull(uniqueDataTypes, "Unique data types set should not be null");
        assertEquals(3, uniqueDataTypes.size(), "Unique data types size should be 3");
        assertTrue(uniqueDataTypes.contains("Uniform"), "Unique data types should contain 'Uniform'");
        assertTrue(uniqueDataTypes.contains("Gaussian"), "Unique data types should contain 'Gaussian'");
        assertTrue(uniqueDataTypes.contains("Exponential"), "Unique data types should contain 'Exponential'");
    }

    @Test
    void testGetUniqueOperations() {
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "MD5", 100, "Uniform", 1000, 10, "Put", 123.45));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("Linear Probing", "SHA1", 200, "Gaussian", 2000, 20, "Get", 678.90));
        resultDataConfig.addResult(new ResultDataConfig.TestResult("BST", "SHA256", 300, "Exponential", 3000, 30, "Delete", 345.67));

        Set<String> uniqueOperations = resultDataConfig.getUniqueOperations();
        assertNotNull(uniqueOperations, "Unique operations set should not be null");
        assertEquals(3, uniqueOperations.size(), "Unique operations size should be 3");
        assertTrue(uniqueOperations.contains("Put"), "Unique operations should contain 'Put'");
        assertTrue(uniqueOperations.contains("Get"), "Unique operations should contain 'Get'");
        assertTrue(uniqueOperations.contains("Delete"), "Unique operations should contain 'Delete'");
    }
}
