package hashlab.ui.app;

import hashlab.algorithms.registry.LanguageRegistry;
import hashlab.tests.HashTestConfig;
import hashlab.ui.components.TestsListInterface;
import hashlab.ui.components.UIComponentProviderInterface;
import hashlab.utils.DataGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HashLabEventHandler {


    private UIComponentProviderInterface uiComponentProvider;
    private NewHashLabAppController controller;
    private Stage primaryStage;
    private TestsListInterface tests;
    private ResourceBundle classLanguageBundle;



    public HashLabEventHandler(UIComponentProviderInterface uiComponentProvider, Stage primaryStage, TestsListInterface tests){
        this.uiComponentProvider = uiComponentProvider;
        this.controller = new NewHashLabAppController();
        this.primaryStage = primaryStage;
        this.tests = tests;
        this.classLanguageBundle = ResourceBundle.getBundle("languages.AppTexts", Locale.ENGLISH);
        controller.initialize(this);
    }

    public void attachEventHandlers() {
        ComboBox<String> languageComboBox = uiComponentProvider.getLanguageComboBox();
        languageComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.equals(oldValue)){
                changeLanguage(newValue);
            }
        }));


        ComboBox<String> algorithmChoiceComboBox = uiComponentProvider.getAlgorithmChoice();
        monitorChanges(algorithmChoiceComboBox);

        TextField hashTableSizeField = uiComponentProvider.getHashTableSizeField();
        monitorChanges(hashTableSizeField, "^[1-9]\\d*$");

        TextField chunkSizeField = uiComponentProvider.getChunkSizeField();
        monitorChanges(chunkSizeField, "^[1-9]\\d*$");

        TextField dataSizeField = uiComponentProvider.getDataSizeField();
        monitorChanges(dataSizeField, "^[1-9]\\d*$");

        CheckListView<String> hashFunctionCheckListView = uiComponentProvider.getHashFunctionChoice();
        monitorChanges(hashFunctionCheckListView);

        CheckBox putCheckBox = uiComponentProvider.getPutCheckbox();
        monitorChanges(putCheckBox);

        CheckBox getCheckBox = uiComponentProvider.getGetCheckbox();
        monitorChanges(getCheckBox);

        CheckBox deleteCheckBox = uiComponentProvider.getDeleteCheckbox();
        monitorChanges(deleteCheckBox);

        RadioButton generateDataRadio = uiComponentProvider.getGenerateDataRadio();
        generateDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());
        monitorChanges(generateDataRadio);

        RadioButton loadDataRadio = uiComponentProvider.getLoadDataRadio();
        loadDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());
        monitorChanges(loadDataRadio);

        CheckBox uniformCheckBox = uiComponentProvider.getUniformCheckBox();
        uniformCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());
        monitorChanges(uniformCheckBox);

        Button generateUniformDataButton = uiComponentProvider.getGenerateUniformDataButton();
        generateUniformDataButton.setOnAction(event -> handleUniformDataGeneration());

        CheckBox gaussianCheckBox = uiComponentProvider.getGaussianCheckBox();
        gaussianCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());
        monitorChanges(gaussianCheckBox);

        TextField meanField = uiComponentProvider.getMeanField();
        monitorChanges(meanField, "^-?\\d+(\\.\\d+)?$");

        TextField deviationField = uiComponentProvider.getDeviationField();
        monitorChanges(deviationField, "^(?:[1-9]\\d*|0\\.\\d+|\\d+\\.\\d+)$");

        Button generateGaussianDataButton = uiComponentProvider.getGenerateGaussianDataButton();
        generateGaussianDataButton.setOnAction(event -> handleGaussianDataGeneration());

        CheckBox exponentialCheckBox = uiComponentProvider.getExponentialCheckBox();
        exponentialCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateUIBasedOnSelection());
        monitorChanges(exponentialCheckBox);

        TextField lambdaField = uiComponentProvider.getLambdaField();
        monitorChanges(lambdaField, "^(?:[1-9]\\d*|0\\.\\d+|\\d+\\.\\d+)$");

        Button generateExponentialDataButton = uiComponentProvider.getGenerateExponentialDataButton();
        generateExponentialDataButton.setOnAction(event -> handleExponentialDataGeneration());

        Button fileChooserButton = uiComponentProvider.getFileChooserButton();
        fileChooserButton.setOnAction(event -> handleFileChooser());

        TextField filePathField = uiComponentProvider.getFilePathField();
        monitorChanges(filePathField, "^.+$");

        TextField benchmarkIterationsField = uiComponentProvider.getBenchmarkIterationsField();
        monitorChanges(benchmarkIterationsField, "^[1-9]\\d*$");

        TextField benchmarkThresholdField = uiComponentProvider.getBenchmarkThresholdField();
        monitorChanges(benchmarkThresholdField, "^-?(?:[1-9]\\d*|0\\.\\d+|\\d+\\.\\d+)$");

        TextField testIterationsField = uiComponentProvider.getTestIterationsField();
        monitorChanges(testIterationsField, "^[1-9]\\d*$");

        TextField testThresholdField = uiComponentProvider.getTestThresholdField();
        monitorChanges(testThresholdField, "^-?(?:[1-9]\\d*|0\\.\\d+|\\d+\\.\\d+)$");

        TextField warmupIterationsField = uiComponentProvider.getWarmupIterationsField();
        monitorChanges(warmupIterationsField, "^[0-9]\\d*$");

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

        Button loadCSVFileButton = uiComponentProvider.getLoadCSVFileButton();
        loadCSVFileButton.setOnAction(event -> handleLoadCSVFile());

        CheckListView<HashTestConfig> testCheckListView = uiComponentProvider.getTestCheckListView();
        testCheckListView.setOnMouseClicked(event -> handleTestCheckList(event));
    }

    private void changeLanguage(String language){
        Locale locale = LanguageRegistry.getLocale(language);
        ResourceBundle languageBundle = ResourceBundle.getBundle("languages.AppTexts", locale);
        classLanguageBundle = ResourceBundle.getBundle("languages.AppTexts", locale);

        ComboBox<String> languageComboBox = uiComponentProvider.getLanguageComboBox();
        languageComboBox.getTooltip().setText(languageBundle.getString("languageComboBox.tooltip"));

        TitledPane languagePane = uiComponentProvider.getLanguagePane();
        languagePane.setText(languageBundle.getString("languagePane.text"));

        ComboBox<String> algorithmComboBox = uiComponentProvider.getAlgorithmChoice();
        algorithmComboBox.getTooltip().setText(languageBundle.getString("algorithmComboBox.tooltip"));

        TextField hashTableSizeField = uiComponentProvider.getHashTableSizeField();
        hashTableSizeField.setPromptText(languageBundle.getString("hashTableSizeField.promptText"));
        hashTableSizeField.getTooltip().setText(languageBundle.getString("hashTableSizeField.tooltip"));

        TextField chunkSizeField = uiComponentProvider.getChunkSizeField();
        chunkSizeField.setPromptText(languageBundle.getString("chunkSizeField.promptText"));
        chunkSizeField.getTooltip().setText(languageBundle.getString("chunkSizeField.tooltip"));

        TitledPane hashAlgorithmsPane = uiComponentProvider.getHashAlgorithmsPane();
        hashAlgorithmsPane.setText(languageBundle.getString("hashAlgorithmsPane.text"));

        CheckListView<String> hashFunctionChoice = uiComponentProvider.getHashFunctionChoice();
        hashFunctionChoice.getTooltip().setText("hashFunctionChoice.tooltip");

        TitledPane hashFunctionsPane = uiComponentProvider.getHashFunctionsPane();
        hashFunctionsPane.setText(languageBundle.getString("hashFunctionsPane.text"));

        TitledPane testOperationsPane = uiComponentProvider.getTestOperationsPane();
        testOperationsPane.setText(languageBundle.getString("testOperationsPane.text"));
        testOperationsPane.getTooltip().setText("testOperationsPane.tooltip");

        RadioButton generateDataRadio = uiComponentProvider.getGenerateDataRadio();
        generateDataRadio.setText(languageBundle.getString("generateDataRadio.text"));
        generateDataRadio.getTooltip().setText("generateDataRadio.tooltip");

        TextField dataSizeField = uiComponentProvider.getDataSizeField();
        dataSizeField.setPromptText(languageBundle.getString("dataSizeField.promptText"));
        dataSizeField.getTooltip().setText("dataSizeField.tooltip");

        ToggleSwitch dataGenerationTimingSwitch = uiComponentProvider.getDataGenerationTimingSwitch();
        dataGenerationTimingSwitch.setText(languageBundle.getString("dataGenerationTimingSwitch.text"));
        dataGenerationTimingSwitch.getTooltip().setText("dataGenerationTimingSwitch.tooltip");

        TitledPane generateDataPane = uiComponentProvider.getGenerateDataPane();
        generateDataPane.setText(languageBundle.getString("generateDataPane.text"));

        TitledPane dataGenerationPane = uiComponentProvider.getDataGenerationPane();
        dataGenerationPane.setText(languageBundle.getString("dataGenerationPane.text"));

        CheckBox uniformCheckBox = uiComponentProvider.getUniformCheckBox();
        uniformCheckBox.setText(languageBundle.getString("uniformCheckBox.text"));
        uniformCheckBox.getTooltip().setText("uniformCheckBox.tooltip");

        Button generateUniformDataButton = uiComponentProvider.getGenerateUniformDataButton();
        generateUniformDataButton.setText(languageBundle.getString("generateUniformDataButton.text"));
        generateUniformDataButton.getTooltip().setText("generateUniformDataButton.tooltip");

        TitledPane uniformPane = uiComponentProvider.getUniformPane();
        uniformPane.setText(languageBundle.getString("uniformPane.text"));

        CheckBox gaussianCheckBox = uiComponentProvider.getGaussianCheckBox();
        gaussianCheckBox.setText(languageBundle.getString("gaussianCheckBox.text"));
        gaussianCheckBox.getTooltip().setText("gaussianCheckBox.tooltip");

        TextField meanField = uiComponentProvider.getMeanField();
        meanField.setPromptText(languageBundle.getString("meanField.promptText"));
        meanField.getTooltip().setText(languageBundle.getString("meanField.tooltip"));

        TextField deviationField = uiComponentProvider.getDeviationField();
        deviationField.setPromptText(languageBundle.getString("deviationField.promptText"));
        deviationField.getTooltip().setText(languageBundle.getString("deviationField.tooltip"));

        Button generateGaussianDataButton = uiComponentProvider.getGenerateGaussianDataButton();
        generateGaussianDataButton.setText(languageBundle.getString("generateGaussianDataButton.text"));
        generateGaussianDataButton.getTooltip().setText("generateGaussianDataButton.tooltip");

        TitledPane gaussianPane = uiComponentProvider.getGaussianPane();
        gaussianPane.setText(languageBundle.getString("gaussianPane.text"));

        CheckBox exponentialCheckBox = uiComponentProvider.getExponentialCheckBox();
        exponentialCheckBox.setText(languageBundle.getString("exponentialCheckBox.text"));
        exponentialCheckBox.getTooltip().setText("exponentialCheckBox.tooltip");

        TextField lambdaField = uiComponentProvider.getLambdaField();
        lambdaField.setPromptText(languageBundle.getString("lambdaField.promptText"));
        lambdaField.getTooltip().setText(languageBundle.getString("lambdaField.tooltip"));

        Button generateExponentialDataButton = uiComponentProvider.getGenerateExponentialDataButton();
        generateExponentialDataButton.setText(languageBundle.getString("generateExponentialDataButton.text"));
        generateExponentialDataButton.getTooltip().setText("generateExponentialDataButton.tooltip");

        TitledPane exponentialPane = uiComponentProvider.getExponentialPane();
        exponentialPane.setText(languageBundle.getString("exponentialPane.text"));

        Button fileChooserButton = uiComponentProvider.getFileChooserButton();
        fileChooserButton.setText(languageBundle.getString("fileChooserButton.text"));
        fileChooserButton.getTooltip().setText(languageBundle.getString("fileChooserButton.tooltip"));

        RadioButton loadDataRadio = uiComponentProvider.getLoadDataRadio();
        loadDataRadio.setText(languageBundle.getString("loadDataRadio.text"));
        loadDataRadio.getTooltip().setText(languageBundle.getString("loadDataRadio.tooltip"));

        ToggleSwitch dataLoadingTimingSwitch = uiComponentProvider.getDataLoadingTimingSwitch();
        dataLoadingTimingSwitch.setText(languageBundle.getString("dataLoadingTimingSwitch.text"));
        dataLoadingTimingSwitch.getTooltip().setText(languageBundle.getString("dataLoadingTimingSwitch.tooltip"));

        TitledPane dataSourcePane = uiComponentProvider.getDataSourcePane();
        dataSourcePane.setText(languageBundle.getString("dataSourcePane.text"));

        TextField benchmarkIterationsField = uiComponentProvider.getBenchmarkIterationsField();
        benchmarkIterationsField.setPromptText(languageBundle.getString("benchmarkIterationsField.promptText"));
        benchmarkIterationsField.getTooltip().setText(languageBundle.getString("benchmarkIterationsField.tooltip"));

        TextField benchmarkThresholdField = uiComponentProvider.getBenchmarkThresholdField();
        benchmarkThresholdField.setPromptText(languageBundle.getString("benchmarkThresholdField.promptText"));
        benchmarkThresholdField.getTooltip().setText(languageBundle.getString("benchmarkThresholdField.tooltip"));

        TitledPane benchmarkParamsPane = uiComponentProvider.getBenchmarkParamsPane();
        benchmarkParamsPane.setText(languageBundle.getString("benchmarkParamsPane.text"));

        TextField testIterationsField = uiComponentProvider.getTestIterationsField();
        testIterationsField.setPromptText(languageBundle.getString("testIterationsField.promptText"));
        testIterationsField.getTooltip().setText(languageBundle.getString("testIterationsField.tooltip"));

        TextField testThresholdField = uiComponentProvider.getTestThresholdField();
        testThresholdField.setPromptText(languageBundle.getString("testThresholdField.promptText"));
        testThresholdField.getTooltip().setText(languageBundle.getString("testThresholdField.tooltip"));

        TextField warmupIterationsField = uiComponentProvider.getWarmupIterationsField();
        warmupIterationsField.setPromptText(languageBundle.getString("warmupIterationsField.promptText"));
        warmupIterationsField.getTooltip().setText(languageBundle.getString("warmupIterationsField.tooltip"));

        TitledPane additionalSettingsPane = uiComponentProvider.getAdditionalSettingsPane();
        additionalSettingsPane.setText(languageBundle.getString("additionalSettingsPane.text"));

        Button runTestButton = uiComponentProvider.getRunTestButton();
        runTestButton.setText(languageBundle.getString("runTestButton.text"));
        runTestButton.getTooltip().setText("runTestButton.tooltip");

        Button addTestButton = uiComponentProvider.getAddTestButton();
        addTestButton.setText(languageBundle.getString("addTestButton.text"));
        addTestButton.getTooltip().setText("addTestButton.tooltip");

        Button removeTestButton = uiComponentProvider.getRemoveTestButton();
        removeTestButton.setText(languageBundle.getString("removeTestButton.text"));
        removeTestButton.getTooltip().setText("removeTestButton.tooltip");

        Button exportSelectedTestsButton = uiComponentProvider.getExportSelectedTestsButton();
        exportSelectedTestsButton.setText(languageBundle.getString("exportSelectedTestsButton.text"));
        exportSelectedTestsButton.getTooltip().setText("exportSelectedTestsButton.tooltip");

        Button importTestsButton = uiComponentProvider.getImportTestsButton();
        importTestsButton.setText(languageBundle.getString("importTestsButton.text"));
        importTestsButton.getTooltip().setText("importTestsButton.tooltip");

        Button loadCSVFileButton = uiComponentProvider.getLoadCSVFileButton();
        loadCSVFileButton.setText(languageBundle.getString("loadCSVFileButton.text"));
        loadCSVFileButton.getTooltip().setText("loadCSVFileButton.tooltip");

        Label testsListLabel = uiComponentProvider.getTestsListLabel();
        testsListLabel.setText(languageBundle.getString("testsListLabel.text"));

        CheckListView<HashTestConfig> testCheckListView = uiComponentProvider.getTestCheckListView();
        testCheckListView.getTooltip().setText(languageBundle.getString("testCheckListView.tooltip"));
    }

    private void monitorChanges(TextField textField, String validPattern){
        textField.getStyleClass().add("text-field-default");

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(validPattern)) {
                if (!newValue.equals(oldValue)) {
                    textField.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-green");
                    textField.getStyleClass().add("text-field-blue");
                }
            } else {
                textField.getStyleClass().removeAll("text-field-default", "text-field-blue", "text-field-green");
                textField.getStyleClass().add("text-field-red");
            }
        });
    }

    private void monitorChanges(ComboBox<String> comboBox) {
        comboBox.getStyleClass().add("text-field-default");

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                comboBox.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-green");
                comboBox.getStyleClass().add("text-field-blue");
            } else {
                comboBox.getStyleClass().removeAll("text-field-default", "text-field-blue", "text-field-green");
                comboBox.getStyleClass().add("text-field-red");
            }
        });
    }

    private void monitorChanges(CheckListView<String> checkListView) {
        checkListView.getStyleClass().add("text-field-default");

        checkListView.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) change -> {
            if (checkListView.getCheckModel().getCheckedItems().isEmpty()) {
                checkListView.getStyleClass().removeAll("text-field-default", "text-field-blue", "text-field-green");
                checkListView.getStyleClass().add("text-field-red");
            } else {
                checkListView.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-green");
                checkListView.getStyleClass().add("text-field-blue");
            }
        });
    }

    private void monitorChanges(CheckBox checkBox){
        checkBox.getStyleClass().add("text-field-default");

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            checkBox.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-green");
            checkBox.getStyleClass().add("text-field-blue");
        });

    }

    private void monitorChanges(RadioButton radioButton){
        radioButton.getStyleClass().add("text-field-default");

        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            radioButton.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-green");
            radioButton.getStyleClass().add("text-field-blue");
        });
    }

    private void setFieldsGreen() {
        uiComponentProvider.getAlgorithmChoice().getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-blue");
        uiComponentProvider.getAlgorithmChoice().getStyleClass().add("text-field-green");

        TextField[] textFields = {
                uiComponentProvider.getHashTableSizeField(),
                uiComponentProvider.getChunkSizeField(),
                uiComponentProvider.getDataSizeField(),
                uiComponentProvider.getMeanField(),
                uiComponentProvider.getDeviationField(),
                uiComponentProvider.getLambdaField(),
                uiComponentProvider.getFilePathField(),
                uiComponentProvider.getBenchmarkIterationsField(),
                uiComponentProvider.getBenchmarkThresholdField(),
                uiComponentProvider.getTestIterationsField(),
                uiComponentProvider.getTestThresholdField(),
                uiComponentProvider.getWarmupIterationsField()
        };

        for (TextField textField : textFields) {
            textField.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-blue");
            textField.getStyleClass().add("text-field-green");
        }

        CheckListView<String> hashFunctionCheckListView = uiComponentProvider.getHashFunctionChoice();
        hashFunctionCheckListView.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-blue");
        hashFunctionCheckListView.getStyleClass().add("text-field-green");

        CheckBox[] checkBoxes = {
                uiComponentProvider.getPutCheckbox(),
                uiComponentProvider.getGetCheckbox(),
                uiComponentProvider.getDeleteCheckbox(),
                uiComponentProvider.getUniformCheckBox(),
                uiComponentProvider.getGaussianCheckBox(),
                uiComponentProvider.getExponentialCheckBox()
        };

        for (CheckBox checkBox : checkBoxes) {
            checkBox.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-blue");
            checkBox.getStyleClass().add("text-field-green");
        }

        RadioButton[] radioButtons = {
                uiComponentProvider.getGenerateDataRadio(),
                uiComponentProvider.getLoadDataRadio()
        };

        for (RadioButton radioButton : radioButtons) {
            radioButton.getStyleClass().removeAll("text-field-default", "text-field-red", "text-field-blue");
            radioButton.getStyleClass().add("text-field-green");
        }
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
        alert.setTitle(classLanguageBundle.getString("alert.title.testDetails"));
        alert.setHeaderText(test.getTestName());
        alert.setContentText(test.toString());

        ButtonType buttonUniform = new ButtonType(classLanguageBundle.getString("button.uniformData"));
        ButtonType buttonGaussian = new ButtonType(classLanguageBundle.getString("button.gaussianData"));
        ButtonType buttonExponential = new ButtonType(classLanguageBundle.getString("button.exponentialData"));
        ButtonType buttonFileData = new ButtonType(classLanguageBundle.getString("button.fileData"));


        if (test.isUniformSelected()) {
            alert.getButtonTypes().add(buttonUniform);
        }
        if (test.isGaussianSelected()) {
            alert.getButtonTypes().add(buttonGaussian);
        }
        if (test.isExponentialSelected()) {
            alert.getButtonTypes().add(buttonExponential);
        }
        if (!test.isDataGenerated()) {
            alert.getButtonTypes().add(buttonFileData);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonUniform) {
                checkAndDisplayHistogram(test.getUniformDataString());
            } else if (result.get() == buttonGaussian) {
                checkAndDisplayHistogram(test.getGaussianDataString());
            } else if (result.get() == buttonExponential) {
                checkAndDisplayHistogram(test.getExponentialDataString());
            } else if (result.get() == buttonFileData) {
                if (test.getLoadedDataString() == null || test.getLoadedDataString().isEmpty()) {
                    test.setLoadedDataString(loadDataFromFile(test.getSelectedFilePath()));
                }
                checkAndDisplayHistogram(test.getLoadedDataString());
            }
        }
    }

    private void checkAndDisplayHistogram(String data){
        if (data == null || data.isEmpty()) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.noData"));
            return;
        }
        showHistogram(data);
    }

    private void handleLoadCSVFile(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            showChart(file.getAbsolutePath());
        }
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
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidDataFormat"));
        } catch (Exception e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.dataGenerationProblem"));
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
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidDataFormat"));
        } catch (Exception exception){
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.dataGenerationProblem"));
        }

    }

    private void handleExponentialDataGeneration(){
        try{
            String sampleData = DataGenerator.generateExponentialASCIIValue(
                    Double.parseDouble(uiComponentProvider.getLambdaField().getText()),
                    Integer.parseInt(uiComponentProvider.getDataSizeField().getText()));
            showHistogram(sampleData);
        } catch (NumberFormatException exception) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidDataFormat"));
        } catch (Exception exception){
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.dataGenerationProblem"));
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
        dialog.setTitle(classLanguageBundle.getString("dialog.title.histogram"));
        dialog.show();

        nudgeWindow(dialog);
    }

    public void showChart(String resultFilePath){
        final Stage dialog = new Stage();
        SwingNode swingNode = new SwingNode();


        SwingUtilities.invokeLater(() -> {
            ChartController chartController = new ChartController(resultFilePath);
            swingNode.setContent(chartController.getMainPanel());
        });

        StackPane dialogLayout = new StackPane();
        dialogLayout.getChildren().add(swingNode);

        Scene dialogScene = new Scene(dialogLayout, 1600, 900);
        dialog.setScene(dialogScene);
        dialog.setTitle(classLanguageBundle.getString("dialog.title.resultCharts"));
        dialog.show();
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

        TextInputDialog fileDialog = new TextInputDialog(classLanguageBundle.getString("dialog.resultFileName.textInput"));
        fileDialog.setTitle(classLanguageBundle.getString("dialog.resultFileName.title"));
        fileDialog.setHeaderText(classLanguageBundle.getString("dialog.resultFileName.header"));
        fileDialog.setContentText(classLanguageBundle.getString("dialog.resultFileName.content"));

        Optional<String> resultFileName = fileDialog.showAndWait();

        List<HashTestConfig> selectedTests = new ArrayList<>(testCheckListView.getCheckModel().getCheckedItems());

        resultFileName.ifPresent(name -> controller.runTests(name, selectedTests));
    }

    private void handleAddTest(){

        if(!validateTestConfig()){
            return;
        }

        TextInputDialog dialog = new TextInputDialog("Test " + (tests.getTestsList().size() + 1));
        dialog.setTitle(classLanguageBundle.getString("dialog.testName.title"));
        dialog.setHeaderText(classLanguageBundle.getString("dialog.testName.header"));
        dialog.setContentText(classLanguageBundle.getString("dialog.testName.content"));

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
                config.setTestIterations(Integer.parseInt(uiComponentProvider.getTestIterationsField().getText()));
                config.setTestThreshold(Double.parseDouble(uiComponentProvider.getTestThresholdField().getText()));
                config.setWarmupIterations(Integer.parseInt(uiComponentProvider.getWarmupIterationsField().getText()));

                tests.addTest(config);
                uiComponentProvider.getTestCheckListView().setItems(FXCollections.observableArrayList(tests.getTestsList()));
                setFieldsGreen();
            } catch (NumberFormatException ex) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.addTestNumeric.content"));
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
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.fileLoadingFailed.part1") + " " + filePath + classLanguageBundle.getString("error.fileLoadingFailed.part2"));
            e.printStackTrace();
            return "";
        }
        return contentBuilder.toString();
    }

    private boolean validateTestConfig(){

        if (uiComponentProvider.getAlgorithmChoice().getValue() == null || uiComponentProvider.getAlgorithmChoice().getValue().isEmpty()) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.selectHashAlgorithm"));
            return false;
        }

        try {
            int hashTableSize = Integer.parseInt(uiComponentProvider.getHashTableSizeField().getText());
            if (hashTableSize <= 0) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidHashTableSize"));
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidHashTableFormat"));
            return false;
        }

        try{
            int chunkSize = Integer.parseInt(uiComponentProvider.getChunkSizeField().getText());
            if (chunkSize <= 0) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidChunkSize"));
                return false;
            }
        } catch(NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidChunkFormat"));
            return false;
        }

        if (uiComponentProvider.getHashFunctionChoice().getCheckModel().getCheckedItems().isEmpty()) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.selectHashFunction"));
            return false;
        }

        if (!(uiComponentProvider.getPutCheckbox().isSelected() || uiComponentProvider.getGetCheckbox().isSelected() || uiComponentProvider.getDeleteCheckbox().isSelected())) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.selectTestOperation"));
            return false;
        }

        if (!uiComponentProvider.getGenerateDataRadio().isSelected() && !uiComponentProvider.getLoadDataRadio().isSelected()) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.selectDataMethod"));
            return false;
        }

        if (uiComponentProvider.getGenerateDataRadio().isSelected()) {
            try {
                int dataSize = Integer.parseInt(uiComponentProvider.getDataSizeField().getText());
                if (dataSize <= 0) {
                    showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidDataSize"));
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidDataSizeFormat"));
                return false;
            }

            if (!uiComponentProvider.getUniformCheckBox().isSelected() && !uiComponentProvider.getGaussianCheckBox().isSelected() && !uiComponentProvider.getExponentialCheckBox().isSelected()) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.selectDataGenerationMethod"));
                return false;
            }
        }


        if (uiComponentProvider.getLoadDataRadio().isSelected() && (uiComponentProvider.getFilePathField().getText().isEmpty())) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.fileNotChosen"));
            return false;
        }



        if (uiComponentProvider.getGenerateDataRadio().isSelected()) {

            if (uiComponentProvider.getGaussianCheckBox().isSelected()) {
                try {
                    Double.parseDouble(uiComponentProvider.getMeanField().getText());
                    Double.parseDouble(uiComponentProvider.getDeviationField().getText());
                } catch (NumberFormatException e) {
                    showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidGaussianParams"));
                    return false;
                }
            }

            if (uiComponentProvider.getExponentialCheckBox().isSelected()) {
                try {
                    Double.parseDouble(uiComponentProvider.getLambdaField().getText());
                } catch (NumberFormatException e) {
                    showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidLambda"));
                    return false;
                }
            }
        }

        try {
            int benchmarkIterations = Integer.parseInt(uiComponentProvider.getBenchmarkIterationsField().getText());
            if (benchmarkIterations <= 0) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidBenchmarkIterations"));
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidBenchmarkIterationsFormat"));
            return false;
        }

        try {
            Double.parseDouble(uiComponentProvider.getBenchmarkThresholdField().getText());
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidBenchmarkThreshold"));
            return false;
        }

        try {
            int testIterations = Integer.parseInt(uiComponentProvider.getTestIterationsField().getText());
            if (testIterations <= 0) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidTestIterations"));
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidTestIterationsFormat"));
            return false;
        }

        try {
            Double.parseDouble(uiComponentProvider.getTestThresholdField().getText());
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidTestThreshold"));
            return false;
        }

        try {
            int warmupIterations = Integer.parseInt(uiComponentProvider.getWarmupIterationsField().getText());
            if (warmupIterations <= 0) {
                showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidWarmupIterations"));
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(classLanguageBundle.getString("error.error"), classLanguageBundle.getString("error.invalidWarmupIterationsFormat"));
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
