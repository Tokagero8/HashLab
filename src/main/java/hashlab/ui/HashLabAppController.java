package hashlab.ui;

import hashlab.core.HashTestConfig;
import hashlab.core.TestConfigManager;
import hashlab.core.TestManager;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class HashLabAppController {

    private HashLabApp view;
    private TestConfigManager testConfigManager = new TestConfigManager();
    private TestManager testManager = new TestManager();

    public void initialize(HashLabApp view){
        this.view = view;
        testConfigManager.initialize(this);
        testManager.initialize(this);
    }

    boolean validateTestConfig(ComboBox<String> algorithmChoice, TextField hashTableSizeField,
                                       CheckListView<String> hashFunctionChoice, CheckBox putCheckbox,
                                       CheckBox getCheckbox, CheckBox deleteCheckbox, RadioButton generateDataRadio,
                                       RadioButton loadDataRadio, File selectedFile, CheckBox uniformCheckBox,
                                       CheckBox gaussianCheckBox, CheckBox exponentialCheckBox, TextField minField,
                                       TextField maxField, TextField meanField, TextField deviationField,
                                       TextField lambdaField, TextField dataSizeField, TextField benchmarkIterationsField,
                                       TextField benchmarkThresholdField) {

        if (algorithmChoice.getValue() == null || algorithmChoice.getValue().isEmpty()) {
            showAlert("Error", "Please select a hashing algorithm.");
            return false;
        }

        try {
            int hashTableSize = Integer.parseInt(hashTableSizeField.getText());
            if (hashTableSize <= 0) {
                showAlert("Error", "Hash table size must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Hash table size must be a valid integer.");
            return false;
        }

        if (hashFunctionChoice.getCheckModel().getCheckedItems().isEmpty()) {
            showAlert("Error", "Please select at least one hash function.");
            return false;
        }

        if (!(putCheckbox.isSelected() || getCheckbox.isSelected() || deleteCheckbox.isSelected())) {
            showAlert("Error", "Please select at least one test operation.");
            return false;
        }

        if (!generateDataRadio.isSelected() && !loadDataRadio.isSelected()) {
            showAlert("Error", "Please select a data generation method or choose to load data from a file.");
            return false;
        }

        if (generateDataRadio.isSelected()) {
            try {
                int dataSize = Integer.parseInt(dataSizeField.getText());
                if (dataSize <= 0) {
                    showAlert("Error", "Data size must be a positive integer.");
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid data size. Please enter a valid integer.");
                return false;
            }

            if (!uniformCheckBox.isSelected() && !gaussianCheckBox.isSelected() && !exponentialCheckBox.isSelected()) {
                showAlert("Error", "Please select at least one data generation method.");
                return false;
            }
        }

        if (loadDataRadio.isSelected() && (selectedFile == null || !selectedFile.exists())) {
            showAlert("Error", "Please choose a valid file to load data from.");
            return false;
        }

        if (generateDataRadio.isSelected()) {
            if (uniformCheckBox.isSelected()) {
                try {
                    double min = Double.parseDouble(minField.getText());
                    double max = Double.parseDouble(maxField.getText());
                    if (min >= max) {
                        showAlert("Error", "In Uniform, 'Min' must be less than 'Max'.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for Uniform parameters.");
                    return false;
                }
            }

            if (gaussianCheckBox.isSelected()) {
                try {
                    Double.parseDouble(meanField.getText());
                    Double.parseDouble(deviationField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for Gaussian parameters.");
                    return false;
                }
            }

            if (exponentialCheckBox.isSelected()) {
                try {
                    Double.parseDouble(lambdaField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter a valid number for Lambda.");
                    return false;
                }
            }
        }

        try {
            int iterations = Integer.parseInt(benchmarkIterationsField.getText());
            if (iterations <= 0) {
                showAlert("Error", "Benchmark iterations must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Benchmark iterations must be a valid integer.");
            return false;
        }

        try {
            Double.parseDouble(benchmarkThresholdField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Benchmark threshold must be a valid number.");
            return false;
        }

        return true;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void showTestDetails(HashTestConfig test) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test details");
        alert.setHeaderText(test.getTestName());
        alert.setContentText(test.toString());

        alert.showAndWait();
    }

    void runTests (String resultFileName, File selectedFile, List<HashTestConfig> selectedTests){
        testManager.runTests(resultFileName, selectedFile, selectedTests);
    }

    public void setButtonsDisabled(boolean disabled) {
        view.runTestButton.setDisable(disabled);
        view.addTestButton.setDisable(disabled);
        view.removeTestButton.setDisable(disabled);
        view.exportSelectedTestsButton.setDisable(disabled);
        view.importTestsButton.setDisable(disabled);
    }

    public void addAllTests(List<HashTestConfig> importedTests){
        view.tests.addAll(importedTests);
    }

    public void updateTestListView() {
        view.testCheckListView.setItems(FXCollections.observableArrayList(view.tests));
    }

    void importTestsAndAdd(Stage primaryStage){
        testConfigManager.importTestsAndAdd(primaryStage);
    }

    void exportSelectedTests(Stage primaryStage, List<HashTestConfig> selectedTests){
        testConfigManager.exportSelectedTests(primaryStage, selectedTests);
    }

}
