package hashlab.ui;

import hashlab.core.HashTestConfig;
import hashlab.core.TestConfigManager;
import hashlab.core.TestManager;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.util.List;

public class NewHashLabAppController {

    private HashLabEventHandler view;
    private final TestConfigManager testConfigManager = new TestConfigManager();
    private final TestManager testManager = new TestManager();

    public void initialize(HashLabEventHandler view){
        this.view = view;
        testConfigManager.initialize(this);
        testManager.initialize(this);
    }

    void runTests (String resultFileName, List<HashTestConfig> selectedTests){
        testManager.runTests(resultFileName, selectedTests);
    }

    void exportSelectedTests(Stage stage, List<HashTestConfig> selectedTests){
        testConfigManager.exportSelectedTests(stage, selectedTests);
    }

    void importTestsAndAdd(Stage stage){
        testConfigManager.importTestsAndAdd(stage);
    }

    public void setButtonsDisabled(boolean disabled){
        view.setButtonsDisabled(disabled);
    }

    public void addAllTests(List<HashTestConfig> importedTests){
        view.addAllTests(importedTests);
    }

    public void updateTestListView() {
        view.updateTestListView();
    }

    public void showAlert(String title, String message){
        view.showAlert(title, message);
    }

}
