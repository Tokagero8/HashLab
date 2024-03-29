package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.collections.ObservableList;

import java.util.List;

public interface TestsListInterface {

    ObservableList<HashTestConfig> getTestsList();
    void addTest(HashTestConfig test);
    void addAllTests(List<HashTestConfig> importedTests);

}
