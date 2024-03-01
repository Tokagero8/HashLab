package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;

public class UIComponentFactory implements UIComponentFactoryInterface{

    private ComboBox<String> algorithmChoice;
    private TextField hashTableSizeField;
    private TextField chunkSizeField;
    private TitledPane hashAlgorithmsPane;
    private CheckListView<String> hashFunctionChoice;
    private TitledPane hashFunctionsPane;
    private CheckBox putCheckbox;
    private CheckBox getCheckbox;
    private CheckBox deleteCheckbox;
    private TitledPane testOperationsPane;
    private ToggleGroup dataToggleGroup;
    private RadioButton generateDataRadio;
    private TextField dataSizeField;
    private ToggleSwitch dataGenerationTimingSwitch;
    private TitledPane generateDataPane;
    private TitledPane dataGenerationPane;
    private CheckBox uniformCheckBox;
    private TextField minField;
    private TextField maxField;
    private Button generateUniformDataButton;
    private TitledPane uniformPane;
    private CheckBox gaussianCheckBox;
    private TextField meanField;
    private TextField deviationField;
    private Button generateGaussianDataButton;
    private TitledPane gaussianPane;
    private CheckBox exponentialCheckBox;
    private TextField lambdaField;
    private Button generateExponentialDataButton;
    private TitledPane exponentialPane;
    private TextField filePathField;
    private Button fileChooserButton;
    private RadioButton loadDataRadio;
    private ToggleSwitch dataLoadingTimingSwitch;
    private TextField benchmarkIterationsField;
    private TextField benchmarkThresholdField;
    private TitledPane benchmarkParamsPane;
    private TextField testIterationsField;
    private TextField testThresholdField;
    private TextField warmupIterationsField;
    private TitledPane additionalSettingsPane;
    private Button runTestButton;
    private Button addTestButton;
    private Button removeTestButton;
    private Button exportSelectedTestsButton;
    private Button importTestsButton;
    private CheckListView<HashTestConfig> testCheckListView;

    @Override
    public TitledPane createHashAlgorithmsPane() {
        algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("BST", "Linear Probing", "Separate Chaining");

        hashTableSizeField = new TextField();
        hashTableSizeField.setPromptText("Hash table size");

        chunkSizeField = new TextField();
        chunkSizeField.setPromptText("Data chunk size");

        HBox hashAlgorithms = new HBox(10, algorithmChoice, hashTableSizeField, chunkSizeField);
        hashAlgorithmsPane = new TitledPane("Hash algorithms", hashAlgorithms);
        hashAlgorithmsPane.setCollapsible(false);

        return hashAlgorithmsPane;
    }

    @Override
    public TitledPane createHashFunctionsPane() {
        hashFunctionChoice = new CheckListView<>();
        hashFunctionChoice.getItems().addAll("MD5", "SHA1", "SHA256");
        hashFunctionsPane = new TitledPane("Hash functions", hashFunctionChoice);
        hashFunctionsPane.setCollapsible(false);

        return hashFunctionsPane;
    }

    @Override
    public TitledPane createTestOperationsPane() {
        putCheckbox = new CheckBox("Put");
        getCheckbox = new CheckBox("Get");
        deleteCheckbox = new CheckBox("Delete");
        HBox testOperations = new HBox(10, putCheckbox, getCheckbox, deleteCheckbox);
        testOperationsPane = new TitledPane("Test operations", testOperations);
        testOperationsPane.setCollapsible(false);

        return testOperationsPane;
    }

    @Override
    public TitledPane createDataGenerationPane() {
        dataToggleGroup = new ToggleGroup();
        generateDataRadio = new RadioButton("Generate data");
        generateDataRadio.setToggleGroup(dataToggleGroup);

        dataSizeField = new TextField();
        dataSizeField.setPromptText("Data size");
        dataSizeField.setDisable(true);

        dataGenerationTimingSwitch = new ToggleSwitch("Generate data during test addition: ");
        dataGenerationTimingSwitch.setDisable(true);

        VBox dataSizeBox = new VBox(5, generateDataRadio, dataSizeField, dataGenerationTimingSwitch);
        generateDataPane = new TitledPane("Data", dataSizeBox);
        generateDataPane.setCollapsible(false);

        VBox dataGenerationBox = new VBox(5, generateDataPane, createUniformPane(), createGaussianPane(), createExponentialPane());
        dataGenerationPane = new TitledPane("Data Generation Methods", dataGenerationBox);
        dataGenerationPane.setCollapsible(false);

        return dataGenerationPane;
    }

    @Override
    public TitledPane createUniformPane() {
        uniformCheckBox = new CheckBox("Uniform");
        uniformCheckBox.setDisable(true);

        minField = new TextField();
        minField.setDisable(true);
        minField.setPromptText("Min");

        maxField = new TextField();
        maxField.setDisable(true);
        maxField.setPromptText("Max");

        generateUniformDataButton = new Button("Generate Sample Uniform Data");
        generateUniformDataButton.setDisable(true);

        VBox uniformBox = new VBox(5, uniformCheckBox, minField, maxField, generateUniformDataButton);
        uniformPane = new TitledPane("Uniform", uniformBox);
        uniformPane.setCollapsible(false);

        return uniformPane;
    }

    @Override
    public TitledPane createGaussianPane() {
        gaussianCheckBox = new CheckBox("Gaussian");
        gaussianCheckBox.setDisable(true);

        meanField = new TextField();
        meanField.setDisable(true);
        meanField.setPromptText("Mean");

        deviationField = new TextField();
        deviationField.setDisable(true);
        deviationField.setPromptText("Deviation");

        generateGaussianDataButton = new Button("Generate Sample Uniform Data");
        generateGaussianDataButton.setDisable(true);

        VBox gaussianBox = new VBox(5, gaussianCheckBox, meanField, deviationField, generateGaussianDataButton);
        gaussianPane = new TitledPane("Gaussian", gaussianBox);
        gaussianPane.setCollapsible(false);

        return gaussianPane;
    }

    @Override
    public TitledPane createExponentialPane() {
        exponentialCheckBox = new CheckBox("Exponential");
        exponentialCheckBox.setDisable(true);

        lambdaField = new TextField();
        lambdaField.setDisable(true);
        lambdaField.setPromptText("Lambda");

        generateExponentialDataButton = new Button("Generate Sample Exponential Data");
        generateExponentialDataButton.setDisable(true);

        VBox exponentialBox = new VBox(5, exponentialCheckBox, lambdaField, generateExponentialDataButton);
        exponentialPane = new TitledPane("Exponential", exponentialBox);
        exponentialPane.setCollapsible(false);

        return exponentialPane;
    }

    @Override
    public TitledPane createDataSourcePane(Stage primaryStage) {
        filePathField = new TextField();
        filePathField.setDisable(true);
        filePathField.setEditable(false);

        fileChooserButton = new Button("Choose a file");
        fileChooserButton.setDisable(true);

        loadDataRadio = new RadioButton("Load from file");
        loadDataRadio.setToggleGroup(dataToggleGroup);

        dataLoadingTimingSwitch = new ToggleSwitch("Load data during test addition: ");
        dataLoadingTimingSwitch.setDisable(true);

        VBox dataSourceBox = new VBox(5, loadDataRadio, fileChooserButton, filePathField, dataLoadingTimingSwitch);
        TitledPane dataSourcePane = new TitledPane("Data source", dataSourceBox);
        dataSourcePane.setCollapsible(false);

        return dataSourcePane;
    }

    @Override
    public TitledPane createBenchmarkParamsPane() {
        benchmarkIterationsField = new TextField();
        benchmarkIterationsField.setPromptText("Benchmark iterations");

        benchmarkThresholdField = new TextField();
        benchmarkThresholdField.setPromptText("Benchmark threshold");

        VBox benchmarkParamsBox = new VBox(5, benchmarkIterationsField, benchmarkThresholdField);
        benchmarkParamsPane = new TitledPane("Benchmark parameters", benchmarkParamsBox);
        benchmarkParamsPane.setCollapsible(false);

        return benchmarkParamsPane;
    }

    @Override
    public TitledPane createAdditionalSettingsPane() {
        testIterationsField = new TextField();
        testIterationsField.setPromptText("Test iterations");

        testThresholdField = new TextField();
        testThresholdField.setPromptText("Test Threshold");

        warmupIterationsField = new TextField();
        warmupIterationsField.setPromptText("Warmup iterations");

        VBox testParamsBox = new VBox(5, testIterationsField, testThresholdField, warmupIterationsField);
        additionalSettingsPane = new TitledPane("Additional Settings", testParamsBox);
        additionalSettingsPane.setCollapsible(false);

        return additionalSettingsPane;
    }

    @Override
    public Button createRunTestButton() {
        runTestButton = new Button("Run the selected tests");
        return runTestButton;
    }

    @Override
    public Button createAddTestButton() {
        addTestButton = new Button("Add a test");
        return addTestButton;
    }

    @Override
    public Button createRemoveTestButton() {
        removeTestButton = new Button("Delete the selected tests");
        return removeTestButton;
    }

    @Override
    public Button createExportSelectedTestsButton() {
        exportSelectedTestsButton = new Button("Export Selected Tests");
        return exportSelectedTestsButton;
    }

    @Override
    public Button createImportTestsButton() {
        importTestsButton = new Button("Import Tests");
        return importTestsButton;
    }

    @Override
    public CheckListView<HashTestConfig> createTestCheckListView() {

        testCheckListView = new CheckListView<>();
        testCheckListView.setCellFactory(lv -> new CheckBoxListCell<>(testCheckListView::getItemBooleanProperty) {
            @Override
            public void updateItem(HashTestConfig item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getTestName());
                }
            }
        });

        return testCheckListView;
    }


    //Getters
    public ComboBox<String> getAlgorithmChoice() {
        return algorithmChoice;
    }

    public TextField getHashTableSizeField() {
        return hashTableSizeField;
    }

    public TextField getChunkSizeField(){
        return chunkSizeField;
    }

    public TitledPane getHashAlgorithmsPane() {
        return hashAlgorithmsPane;
    }

    public CheckListView<String> getHashFunctionChoice() {
        return hashFunctionChoice;
    }

    public TitledPane getHashFunctionsPane() {
        return hashFunctionsPane;
    }

    public CheckBox getPutCheckbox() {
        return putCheckbox;
    }

    public CheckBox getGetCheckbox() {
        return getCheckbox;
    }

    public CheckBox getDeleteCheckbox() {
        return deleteCheckbox;
    }

    public TitledPane getTestOperationsPane() {
        return testOperationsPane;
    }

    public ToggleGroup getDataToggleGroup() {
        return dataToggleGroup;
    }

    public RadioButton getGenerateDataRadio() {
        return generateDataRadio;
    }

    public TextField getDataSizeField() {
        return dataSizeField;
    }

    public ToggleSwitch getDataGenerationTimingSwitch(){
        return dataGenerationTimingSwitch;
    }

    public TitledPane getGenerateDataPane() {
        return generateDataPane;
    }

    public TitledPane getDataGenerationPane() {
        return dataGenerationPane;
    }

    public CheckBox getUniformCheckBox() {
        return uniformCheckBox;
    }

    public TextField getMinField() {
        return minField;
    }

    public TextField getMaxField() {
        return maxField;
    }

    public Button getGenerateUniformDataButton(){
        return generateUniformDataButton;
    }

    public TitledPane getUniformPane() {
        return uniformPane;
    }

    public CheckBox getGaussianCheckBox() {
        return gaussianCheckBox;
    }

    public TextField getMeanField() {
        return meanField;
    }

    public TextField getDeviationField() {
        return deviationField;
    }

    public Button getGenerateGaussianDataButton(){
        return generateGaussianDataButton;
    }

    public TitledPane getGaussianPane() {
        return gaussianPane;
    }

    public CheckBox getExponentialCheckBox() {
        return exponentialCheckBox;
    }

    public TextField getLambdaField() {
        return lambdaField;
    }

    public Button getGenerateExponentialDataButton(){
        return generateExponentialDataButton;
    }

    public TitledPane getExponentialPane() {
        return exponentialPane;
    }

    public TextField getFilePathField() {
        return filePathField;
    }

    public Button getFileChooserButton() {
        return fileChooserButton;
    }

    public RadioButton getLoadDataRadio() {
        return loadDataRadio;
    }

    public ToggleSwitch getDataLoadingTimingSwitch(){
        return dataLoadingTimingSwitch;
    }

    public TextField getBenchmarkIterationsField() {
        return benchmarkIterationsField;
    }

    public TextField getBenchmarkThresholdField() {
        return benchmarkThresholdField;
    }

    public TitledPane getBenchmarkParamsPane() {
        return benchmarkParamsPane;
    }

    public TextField getTestIterationsField(){
        return testIterationsField;
    }

    public TextField getTestThresholdField(){
        return testThresholdField;
    }

    public TextField getWarmupIterationsField(){
        return warmupIterationsField;
    }

    public TitledPane getAdditionalSettingsPane(){
        return additionalSettingsPane;
    }

    public Button getRunTestButton() {
        return runTestButton;
    }

    public Button getAddTestButton() {
        return addTestButton;
    }

    public Button getRemoveTestButton() {
        return removeTestButton;
    }

    public Button getExportSelectedTestsButton() {
        return exportSelectedTestsButton;
    }

    public Button getImportTestsButton() {
        return importTestsButton;
    }

    public CheckListView<HashTestConfig> getTestCheckListView() {
        return testCheckListView;
    }

}
