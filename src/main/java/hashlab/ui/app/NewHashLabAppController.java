package hashlab.ui.app;

import hashlab.tests.HashTestConfig;
import hashlab.tests.TestConfigImporterExporter;
import hashlab.tests.TestManager;
import hashlab.utils.DataGenerator;
import hashlab.utils.FileUnils;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class NewHashLabAppController {

    private HashLabEventHandler view;
    private ChartDisplay chartDisplay = new ChartDisplay();
    private final TestConfigImporterExporter testConfigImporterExporter = new TestConfigImporterExporter();
    private final TestManager testManager = new TestManager();

    public void initialize(HashLabEventHandler view){
        this.view = view;
        testConfigImporterExporter.initialize(this);
        testManager.initialize(this);
    }

    void runTests (String resultFileName, List<HashTestConfig> selectedTests){
        testManager.runTests(resultFileName, selectedTests);
    }

    void exportSelectedTests(Stage stage, List<HashTestConfig> selectedTests){
        testConfigImporterExporter.exportSelectedTests(stage, selectedTests);
    }

    void importTestsAndAdd(Stage stage){
        testConfigImporterExporter.importTestsAndAdd(stage);
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

    public void showResultChart(String resultFileName){
        view.showChart(FileUnils.getFilePath(resultFileName));
    }

}
