package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TestsList implements TestsListInterface {

    ObservableList<HashTestConfig> tests = FXCollections.observableArrayList();

    @Override
    public ObservableList<HashTestConfig> getTestsList(){
        return tests;
    }

    @Override
    public void addTest(HashTestConfig test) {
        tests.add(test);
    }

    @Override
    public void addAllTests(List<HashTestConfig> importedTests) {
        tests.addAll(importedTests);
    }

}
