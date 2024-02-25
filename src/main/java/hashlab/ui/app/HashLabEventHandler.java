package hashlab.ui.app;

import hashlab.tests.HashTestConfig;
import hashlab.ui.components.TestsListInterface;
import hashlab.ui.components.UIComponentProviderInterface;
import hashlab.utils.DataGenerator;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HashLabEventHandler {


    private UIComponentProviderInterface uiComponentProvider;
    private NewHashLabAppController controller;
    private Stage primaryStage;
    private TestsListInterface tests;



    public HashLabEventHandler(UIComponentProviderInterface uiComponentProvider, Stage primaryStage, TestsListInterface tests){
        this.uiComponentProvider = uiComponentProvider;
        this.controller = new NewHashLabAppController();
        this.primaryStage = primaryStage;
        this.tests = tests;
        controller.initialize(this);
    }

    public void attachEventHandlers() {
        RadioButton generateDataRadio = uiComponentProvider.getGenerateDataRadio();
        generateDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());

        RadioButton loadDataRadio = uiComponentProvider.getLoadDataRadio();
        loadDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());

        CheckBox uniformCheckBox = uiComponentProvider.getUniformCheckBox();
        uniformCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());

        Button generateUniformDataButton = uiComponentProvider.getGenerateUniformDataButton();
        generateUniformDataButton.setOnAction(event -> handleUniformDataGeneration());

        CheckBox gaussianCheckBox = uiComponentProvider.getGaussianCheckBox();
        gaussianCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());

        Button generateGaussainDataButton = uiComponentProvider.getGenerateGaussianDataButton();
        generateGaussainDataButton.setOnAction(event -> handleGaussianDataGeneration());

        CheckBox exponentialCheckBox = uiComponentProvider.getExponentialCheckBox();
        exponentialCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());

        Button generateExponentialDataButton = uiComponentProvider.getGenerateExponentialDataButton();
        generateExponentialDataButton.setOnAction(event -> handleExponenialDataGeneration());

        Button fileChooserButton = uiComponentProvider.getFileChooserButton();
        fileChooserButton.setOnAction(event -> handleFileChooser());

        CheckListView<HashTestConfig> testCheckListView = uiComponentProvider.getTestCheckListView();
        testCheckListView.setOnMouseClicked(event -> handleTestCheckList(event));

        Button runTestButton = uiComponentProvider.getRunTestButton();
        runTestButton.setOnAction(event -> handleRunTest(uiComponentProvider.getTestCheckListView()));

        Button addTestButton = uiComponentProvider.getAddTestButton();
        addTestButton.setOnAction(event -> handleAddTest());

        Button removeTestButton = uiComponentProvider.getRemoveTestButton();
        removeTestButton.setOnAction(event -> handleRemoveTest());

        Button exportButton = uiComponentProvider.getExportSelectedTestsButton();
        exportButton.setOnAction(event -> handleExportTests());

        Button importButton = uiComponentProvider.getImportTestsButton();
        importButton.setOnAction(event -> handleImportTests());

    }

    private void handleTestCheckList(MouseEvent event){
        if (event.getClickCount() == 2) {
            HashTestConfig selectedTest = uiComponentProvider.getTestCheckListView().getSelectionModel().getSelectedItem();
            if (selectedTest != null) {
                showTestDetails(selectedTest);
            }
        }
    }

    private void showTestDetails(HashTestConfig test) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test details");
        alert.setHeaderText(test.getTestName());
        alert.setContentText(test.toString());

        alert.showAndWait();
    }

    private void updateUIBasedOnSelection(){
        boolean isGenerateDataSelected = uiComponentProvider.getGenerateDataRadio().isSelected();
        boolean isLoadDataSelected = uiComponentProvider.getLoadDataRadio().isSelected();

        uiComponentProvider.getDataSizeField().setDisable(!isGenerateDataSelected);
        uiComponentProvider.getDataGenerationTimingSwitch().setDisable(!isGenerateDataSelected);
        uiComponentProvider.getFileChooserButton().setDisable(!isLoadDataSelected);
        uiComponentProvider.getFilePathField().setDisable(!isLoadDataSelected);
        uiComponentProvider.getDataLoadingTimingSwitch().setDisable(!isLoadDataSelected);

        uiComponentProvider.getUniformCheckBox().setDisable(!isGenerateDataSelected);
        uiComponentProvider.getGaussianCheckBox().setDisable(!isGenerateDataSelected);
        uiComponentProvider.getExponentialCheckBox().setDisable(!isGenerateDataSelected);

        uiComponentProvider.getMinField().setDisable(!isGenerateDataSelected || !uiComponentProvider.getUniformCheckBox().isSelected());
        uiComponentProvider.getMaxField().setDisable(!isGenerateDataSelected || !uiComponentProvider.getUniformCheckBox().isSelected());
        uiComponentProvider.getGenerateUniformDataButton().setDisable(!isGenerateDataSelected || !uiComponentProvider.getUniformCheckBox().isSelected());

        uiComponentProvider.getMeanField().setDisable(!isGenerateDataSelected || !uiComponentProvider.getGaussianCheckBox().isSelected());
        uiComponentProvider.getDeviationField().setDisable(!isGenerateDataSelected || !uiComponentProvider.getGaussianCheckBox().isSelected());
        uiComponentProvider.getGenerateGaussianDataButton().setDisable(!isGenerateDataSelected || !uiComponentProvider.getGaussianCheckBox().isSelected());

        uiComponentProvider.getLambdaField().setDisable(!isGenerateDataSelected || !uiComponentProvider.getExponentialCheckBox().isSelected());
        uiComponentProvider.getGenerateExponentialDataButton().setDisable(!isGenerateDataSelected || !uiComponentProvider.getExponentialCheckBox().isSelected());
    }

    private void handleUniformDataGeneration(){
        try {
            String sampleData = DataGenerator.generateUniformASCIIValue(Integer.parseInt(uiComponentProvider.getDataSizeField().getText()));
            showHistogram(sampleData);
        } catch (NumberFormatException exception) {
            showAlert("Error", "Invalid data format. Please enter a correct numeric value.");
        } catch (Exception e) {
            showAlert("Error", "A problem occurred while generating sample data. Please check the validity of the input parameters.");
        }

    }

    private void handleGaussianDataGeneration(){
        try{
            String sampleData = DataGenerator.generateGaussianASCIIValue(
                    Double.parseDouble(uiComponentProvider.getMeanField().getText()),
                    Double.parseDouble(uiComponentProvider.getDeviationField().getText()),
                    Integer.parseInt(uiComponentProvider.getDataSizeField().getText()));
            showHistogram(sampleData);
        } catch (NumberFormatException exception) {
            showAlert("Error", "Invalid data format. Please enter a correct numeric value.");
        } catch (Exception exception){
            showAlert("Error", "A problem occurred while generating sample data. Please check the validity of the input parameters.");
        }

    }

    private void handleExponenialDataGeneration(){
        try{
            String sampleData = DataGenerator.generateExponentialASCIIValue(
                    Double.parseDouble(uiComponentProvider.getLambdaField().getText()),
                    Integer.parseInt(uiComponentProvider.getDataSizeField().getText()));
            showHistogram(sampleData);
        } catch (NumberFormatException exception) {
            showAlert("Error", "Invalid data format. Please enter a correct numeric value.");
        } catch (Exception exception){
            showAlert("Error", "A problem occurred while generating data. Please check the validity of the input parameters.");
        }

    }

    private void showHistogram(String histogramData){
        final Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        SwingNode swingNode = new SwingNode();

        SwingUtilities.invokeLater(() -> {
            HistogramController histogramController = new HistogramController();
            swingNode.setContent(histogramController.createHistogramPanel(histogramData));
        });

        StackPane dialogLayout = new StackPane();
        dialogLayout.getChildren().add(swingNode);

        Scene dialogScene = new Scene(dialogLayout, 1600, 900);
        dialog.setScene(dialogScene);
        dialog.setTitle("Histogram ASCII");
        dialog.show();

        nudgeWindow(dialog);
    }

    private void nudgeWindow(Stage stage) {
        double originalX = stage.getX();
        double originalY = stage.getY();

        stage.setX(originalX + 10);
        stage.setY(originalY + 10);

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            javafx.application.Platform.runLater(() -> {
                stage.setX(originalX);
                stage.setY(originalY);
            });
        }).start();
    }

    private void handleFileChooser(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null){
             uiComponentProvider.getFilePathField().setText(selectedFile.getAbsolutePath());
        }
    }

    private void handleRunTest(CheckListView<HashTestConfig> testCheckListView){

        TextInputDialog fileDialog = new TextInputDialog("results");
        fileDialog.setTitle("Result File Name");
        fileDialog.setHeaderText("Enter the name of the file to save results:");
        fileDialog.setContentText("File name:");

        Optional<String> resultFileName = fileDialog.showAndWait();

        List<HashTestConfig> selectedTests = new ArrayList<>(testCheckListView.getCheckModel().getCheckedItems());

        resultFileName.ifPresent(name -> controller.runTests(name, selectedTests));
    }

    private void handleAddTest(){

        if(!validateTestConfig()){
            return;
        }

        TextInputDialog dialog = new TextInputDialog("Test " + (tests.getTestsList().size() + 1));
        dialog.setTitle("Test name");
        dialog.setHeaderText("Enter a name for the new test:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            HashTestConfig config = new HashTestConfig();

            config.setId(UUID.randomUUID().toString());
            config.setTestName(name);
            config.setAlgorithm(uiComponentProvider.getAlgorithmChoice().getValue());
            config.setHashFunctions(new ArrayList<>(uiComponentProvider.getHashFunctionChoice().getCheckModel().getCheckedItems()));
            config.setPutSelected(uiComponentProvider.getPutCheckbox().isSelected());
            config.setGetSelected(uiComponentProvider.getGetCheckbox().isSelected());
            config.setDeleteSelected(uiComponentProvider.getDeleteCheckbox().isSelected());
            config.setDataGenerated(uiComponentProvider.getGenerateDataRadio().isSelected());
            config.setGeneratedOnAdd(uiComponentProvider.getDataGenerationTimingSwitch().isSelected());
            config.setUniformSelected(uiComponentProvider.getUniformCheckBox().isSelected());
            config.setGaussianSelected(uiComponentProvider.getGaussianCheckBox().isSelected());
            config.setExponentialSelected(uiComponentProvider.getExponentialCheckBox().isSelected());

            try {
                config.setHashTableSize(Integer.parseInt(uiComponentProvider.getHashTableSizeField().getText()));
                config.setChunkSize(Integer.parseInt(uiComponentProvider.getChunkSizeField().getText()));
                if (config.isDataGenerated()) {
                    config.setDataSize(Integer.parseInt(uiComponentProvider.getDataSizeField().getText()));
                    if(config.isUniformSelected()) {
                        config.setMin(Double.parseDouble(uiComponentProvider.getMinField().getText()));
                        config.setMax(Double.parseDouble(uiComponentProvider.getMinField().getText()));
                        if(uiComponentProvider.getDataGenerationTimingSwitch().isSelected()){
                            config.setUniformDataString(DataGenerator.generateUniformASCIIValue(Integer.parseInt(uiComponentProvider.getDataSizeField().getText())));
                        }
                    }
                    if(config.isGaussianSelected()) {
                        config.setMean(Double.parseDouble(uiComponentProvider.getMeanField().getText()));
                        config.setDeviation(Double.parseDouble(uiComponentProvider.getDeviationField().getText()));
                        if(uiComponentProvider.getDataGenerationTimingSwitch().isSelected()){
                            config.setGaussianDataString(DataGenerator.generateGaussianASCIIValue(
                                    Double.parseDouble(uiComponentProvider.getMeanField().getText()),
                                    Double.parseDouble(uiComponentProvider.getDeviationField().getText()),
                                    Integer.parseInt(uiComponentProvider.getDataSizeField().getText())));
                        }
                    }
                    if(config.isExponentialSelected()) {
                        config.setLambda(Double.parseDouble(uiComponentProvider.getLambdaField().getText()));
                        if(uiComponentProvider.getDataGenerationTimingSwitch().isSelected()){
                            config.setExponentialDataString(DataGenerator.generateExponentialASCIIValue(
                                    Double.parseDouble(uiComponentProvider.getLambdaField().getText()),
                                    Integer.parseInt(uiComponentProvider.getDataSizeField().getText())));
                        }

                    }
                } else {
                    config.setSelectedFilePath(uiComponentProvider.getFilePathField().getText());
                    if(uiComponentProvider.getDataLoadingTimingSwitch().isSelected()){
                        config.setLoadedDataString(loadDataFromFile(config.getSelectedFilePath()));
                    }
                }


                config.setBenchmarkIterations(Integer.parseInt(uiComponentProvider.getBenchmarkIterationsField().getText()));
                config.setBenchmarkThreshold(Double.parseDouble(uiComponentProvider.getBenchmarkThresholdField().getText()));

                tests.addTest(config);
                uiComponentProvider.getTestCheckListView().setItems(FXCollections.observableArrayList(tests.getTestsList()));
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter valid numerical values");
            }
        });


    }

    private String loadDataFromFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return contentBuilder.toString();
    }

    private boolean validateTestConfig(){

        if (uiComponentProvider.getAlgorithmChoice().getValue() == null || uiComponentProvider.getAlgorithmChoice().getValue().isEmpty()) {
            showAlert("Error", "Please select a hashing algorithm.");
            return false;
        }

        try {
            int hashTableSize = Integer.parseInt(uiComponentProvider.getHashTableSizeField().getText());
            if (hashTableSize <= 0) {
                showAlert("Error", "Hash table size must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Hash table size must be a valid integer.");
            return false;
        }

        if (uiComponentProvider.getHashFunctionChoice().getCheckModel().getCheckedItems().isEmpty()) {
            showAlert("Error", "Please select at least one hash function.");
            return false;
        }

        if (!(uiComponentProvider.getPutCheckbox().isSelected() || uiComponentProvider.getGetCheckbox().isSelected() || uiComponentProvider.getDeleteCheckbox().isSelected())) {
            showAlert("Error", "Please select at least one test operation.");
            return false;
        }

        if (!(uiComponentProvider.getPutCheckbox().isSelected())){
            showAlert("Error", "Operation PUT must be selected.");
            return false;
        }

        if (!uiComponentProvider.getGenerateDataRadio().isSelected() && !uiComponentProvider.getLoadDataRadio().isSelected()) {
            showAlert("Error", "Please select a data generation method or choose to load data from a file.");
            return false;
        }

        if (uiComponentProvider.getGenerateDataRadio().isSelected()) {
            try {
                int dataSize = Integer.parseInt(uiComponentProvider.getDataSizeField().getText());
                if (dataSize <= 0) {
                    showAlert("Error", "Data size must be a positive integer.");
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid data size. Please enter a valid integer.");
                return false;
            }

            if (!uiComponentProvider.getUniformCheckBox().isSelected() && !uiComponentProvider.getGaussianCheckBox().isSelected() && !uiComponentProvider.getExponentialCheckBox().isSelected()) {
                showAlert("Error", "Please select at least one data generation method.");
                return false;
            }
        }


        if (uiComponentProvider.getLoadDataRadio().isSelected() && (uiComponentProvider.getFilePathField().getText().isEmpty())) {
            showAlert("Error", "Please choose a valid file to load data from.");
            return false;
        }



        if (uiComponentProvider.getGenerateDataRadio().isSelected()) {
            if (uiComponentProvider.getUniformCheckBox().isSelected()) {
                try {
                    double min = Double.parseDouble(uiComponentProvider.getMinField().getText());
                    double max = Double.parseDouble(uiComponentProvider.getMaxField().getText());
                    if (min >= max) {
                        showAlert("Error", "In Uniform, 'Min' must be less than 'Max'.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for Uniform parameters.");
                    return false;
                }
            }

            if (uiComponentProvider.getGaussianCheckBox().isSelected()) {
                try {
                    Double.parseDouble(uiComponentProvider.getMeanField().getText());
                    Double.parseDouble(uiComponentProvider.getDeviationField().getText());
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for Gaussian parameters.");
                    return false;
                }
            }

            if (uiComponentProvider.getExponentialCheckBox().isSelected()) {
                try {
                    Double.parseDouble(uiComponentProvider.getLambdaField().getText());
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter a valid number for Lambda.");
                    return false;
                }
            }
        }

        try {
            int iterations = Integer.parseInt(uiComponentProvider.getBenchmarkIterationsField().getText());
            if (iterations <= 0) {
                showAlert("Error", "Benchmark iterations must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Benchmark iterations must be a valid integer.");
            return false;
        }

        try {
            Double.parseDouble(uiComponentProvider.getBenchmarkThresholdField().getText());
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

    private void handleRemoveTest(){
        List<HashTestConfig> selectedTests = new ArrayList<>(uiComponentProvider.getTestCheckListView().getCheckModel().getCheckedItems());
        tests.getTestsList().removeAll(selectedTests);
        uiComponentProvider.getTestCheckListView().setItems(tests.getTestsList());
        uiComponentProvider.getTestCheckListView().getCheckModel().clearChecks();
    }

    private void handleExportTests(){
        List<HashTestConfig> selectedTests = new ArrayList<>(uiComponentProvider.getTestCheckListView().getCheckModel().getCheckedItems());
        controller.exportSelectedTests(primaryStage, selectedTests);
    }

    private void handleImportTests(){
        controller.importTestsAndAdd(primaryStage);

    }

    public void addAllTests(List<HashTestConfig> importedTests){
        tests.addAllTests(importedTests);
    }

    public void updateTestListView(){
        uiComponentProvider.getTestCheckListView().setItems(FXCollections.observableArrayList(tests.getTestsList()));
    }

    public void setButtonsDisabled(boolean disabled){
        uiComponentProvider.getGenerateUniformDataButton().setDisable(disabled);
        uiComponentProvider.getGenerateGaussianDataButton().setDisable(disabled);
        uiComponentProvider.getGenerateExponentialDataButton().setDisable(disabled);
        uiComponentProvider.getFileChooserButton().setDisable(disabled);
        uiComponentProvider.getRunTestButton().setDisable(disabled);
        uiComponentProvider.getAddTestButton().setDisable(disabled);
        uiComponentProvider.getRemoveTestButton().setDisable(disabled);
        uiComponentProvider.getExportSelectedTestsButton().setDisable(disabled);
        uiComponentProvider.getImportTestsButton().setDisable(disabled);
    }


}
