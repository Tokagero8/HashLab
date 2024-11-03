package hashlab.ui.components;

import hashlab.algorithms.registry.HashRegistry;
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
    private Button loadCSVFileButton;
    private CheckListView<HashTestConfig> testCheckListView;

    @Override
    public TitledPane createHashAlgorithmsPane() {
        algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll(HashRegistry.getHashAlgorithms());
        Tooltip algorithmTooltip = new Tooltip(
                "Select a hashing algorithm to be tested.\n" +
                        "The hashing algorithm determines how data is distributed and accessed in the hash table.\n" +
                        "Different algorithms handle collisions and data distribution in various ways, which can significantly impact performance."
        );
        algorithmTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(algorithmChoice, algorithmTooltip);

        hashTableSizeField = new TextField();
        hashTableSizeField.setPromptText("Hash table size");
        Tooltip hashTableSizeTooltip = new Tooltip(
                "Enter the size of the hash table.\n" +
                        "This value determines the initial capacity of the hash table, which directly affects performance,\n" +
                        "particularly in terms of collision handling and memory usage.\n" +
                        "Please enter a positive integer greater than 0.\n" +
                        "A larger table size can reduce the number of collisions, improving efficiency, but may also use more memory."
        );
        hashTableSizeTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(hashTableSizeField, hashTableSizeTooltip);

        chunkSizeField = new TextField();
        chunkSizeField.setPromptText("Data chunk size");
        Tooltip chunkSizeTooltip = new Tooltip(
                "Enter the size of the data chunk to be hashed.\n" +
                        "This value specifies how many characters will be processed in each hashing operation.\n" +
                        "Please enter a positive integer greater than 0.\n" +
                        "A larger chunk size means more data will be hashed at once, which can impact both performance and memory usage."
        );
        chunkSizeTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(chunkSizeField, chunkSizeTooltip);

        HBox hashAlgorithms = new HBox(10, algorithmChoice, hashTableSizeField, chunkSizeField);
        hashAlgorithmsPane = new TitledPane("Hash algorithms", hashAlgorithms);
        hashAlgorithmsPane.setCollapsible(false);

        return hashAlgorithmsPane;
    }

    @Override
    public TitledPane createHashFunctionsPane() {
        hashFunctionChoice = new CheckListView<>();
        hashFunctionChoice.getItems().addAll(HashRegistry.getHashFunctions());
        hashFunctionsPane = new TitledPane("Hash functions", hashFunctionChoice);
        hashFunctionsPane.setCollapsible(false);
        Tooltip hashFunctionTooltip = new Tooltip(
                "Select at least one hash function to be used in the testing.\n" +
                        "Hash functions are algorithms that map data of arbitrary size to fixed-size values.\n" +
                        "Choosing multiple functions will allow for a comparative analysis of their performance and collision resistance."
        );
        hashFunctionTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(hashFunctionChoice, hashFunctionTooltip);

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
        Tooltip testOperationsTooltip = new Tooltip(
                "Select at least one operation to be performed during the efficiency test.\n" +
                        "Available operations include:\n" +
                        "- Put: Inserts data into the hash table, useful for assessing insertion performance and collision resolution.\n" +
                        "- Get: Retrieves data from the hash table, used to evaluate lookup speed and retrieval efficiency.\n" +
                        "- Delete: Removes data from the hash table, useful for testing the effectiveness of deletion processes and reorganization."
        );
        testOperationsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(testOperationsPane, testOperationsTooltip);

        return testOperationsPane;
    }

    @Override
    public TitledPane createDataGenerationPane() {
        dataToggleGroup = new ToggleGroup();

        generateDataRadio = new RadioButton("Generate data");
        generateDataRadio.setToggleGroup(dataToggleGroup);
        Tooltip generateDataTooltip = new Tooltip(
                "Select this option to generate synthetic data for performance testing.\n" +
                        "Generated data will be used during the performance evaluation of hashing algorithms."
        );
        generateDataTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(generateDataRadio, generateDataTooltip);

        dataSizeField = new TextField();
        dataSizeField.setPromptText("Data size");
        dataSizeField.setDisable(true);
        Tooltip dataSizeTooltip = new Tooltip(
                "Enter the size of the data to be generated for performance testing.\n" +
                        "This value must be a non-negative integer (e.g., 0 or greater).\n" +
                        "Note: For some algorithms, such as Linear Probing, completely filling the hash table can lead to\n" +
                        "performance degradation or failure to insert more elements.\n" +
                        "Ensure the selected data size does not entirely fill the hash table to avoid potential issues."
        );
        dataSizeTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(dataSizeField, dataSizeTooltip);

        dataGenerationTimingSwitch = new ToggleSwitch("Generate data during test addition: ");
        dataGenerationTimingSwitch.setDisable(true);
        Tooltip dataGenerationTimingTooltip = new Tooltip(
                "Choose when to generate the data for the test.\n" +
                        "If enabled, data will be generated when adding the test to the list.\n" +
                        "This ensures that the same data is used every time the test is run, providing consistent results.\n" +
                        "If disabled, data will be generated each time the test is executed, which may lead to different data for each run,\n" +
                        "allowing for a broader evaluation of algorithm performance under varying conditions."
        );
        dataGenerationTimingTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(dataGenerationTimingSwitch, dataGenerationTimingTooltip);

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

        Tooltip uniformTooltip = new Tooltip(
                "Select this option to generate data with a uniform distribution from the ASCII character range (0 to 255).\n" +
                        "A uniform distribution ensures that each possible ASCII character has an equal probability of occurrence.\n" +
                        "This type of data distribution is useful for evaluating how well the hashing algorithm handles evenly distributed\n" +
                        "input values, providing insight into collision handling under idealized conditions."
        );
        uniformTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(uniformCheckBox, uniformTooltip);

        generateUniformDataButton = new Button("Generate Sample Uniform Data");
        generateUniformDataButton.setDisable(true);

        Tooltip generateUniformDataTooltip = new Tooltip(
                "Click this button to generate a sample dataset with a uniform distribution from the ASCII character range (0 to 255).\n" +
                        "The generated data will be visualized on a chart, allowing you to observe the uniform distribution pattern."
        );
        generateUniformDataTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(generateUniformDataButton, generateUniformDataTooltip);

        VBox uniformBox = new VBox(5, uniformCheckBox, generateUniformDataButton);
        uniformPane = new TitledPane("Uniform", uniformBox);
        uniformPane.setCollapsible(false);

        return uniformPane;
    }

    @Override
    public TitledPane createGaussianPane() {
        gaussianCheckBox = new CheckBox("Gaussian");
        gaussianCheckBox.setDisable(true);

        Tooltip gaussianTooltip = new Tooltip(
                "Select this option to generate data with a Gaussian (normal) distribution.\n" +
                        "A Gaussian distribution is characterized by a bell-shaped curve, where most values are concentrated\n" +
                        "around the mean, with fewer values occurring further away.\n" +
                        "This type of data distribution is useful for evaluating how well the hashing algorithm handles\n" +
                        "data that follows a real-world, naturally occurring pattern with clusters of similar values.\n" +
                        "Specify the mean and standard deviation to control the center and spread of the distribution."
        );
        gaussianTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(gaussianCheckBox, gaussianTooltip);

        meanField = new TextField();
        meanField.setDisable(true);
        meanField.setPromptText("Mean");
        Tooltip meanTooltip = new Tooltip(
                "Enter the mean value for the Gaussian (normal) distribution.\n" +
                        "The mean must be a floating-point number, representing the center of the distribution, where most data points will cluster.\n" +
                        "A higher mean will shift the distribution to higher values, while a lower mean will shift it to lower values."
        );
        meanTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(meanField, meanTooltip);

        deviationField = new TextField();
        deviationField.setDisable(true);
        deviationField.setPromptText("Deviation");
        Tooltip deviationTooltip = new Tooltip(
                "Enter the standard deviation for the Gaussian (normal) distribution.\n" +
                        "The standard deviation must be a positive floating-point number, which controls the spread of the distribution.\n" +
                        "A higher standard deviation results in data points being more widely spread out from the mean,\n" +
                        "while a lower value keeps the data points closer to the mean."
        );
        deviationTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(deviationField, deviationTooltip);


        generateGaussianDataButton = new Button("Generate Sample Uniform Data");
        generateGaussianDataButton.setDisable(true);
        Tooltip generateGaussianDataTooltip = new Tooltip(
                "Click this button to generate a sample dataset with a Gaussian (normal) distribution.\n" +
                        "The generated data will be based on the specified mean and standard deviation, allowing you to visualize the distribution pattern on a chart."
        );
        generateGaussianDataTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(generateGaussianDataButton, generateGaussianDataTooltip);

        VBox gaussianBox = new VBox(5, gaussianCheckBox, meanField, deviationField, generateGaussianDataButton);
        gaussianPane = new TitledPane("Gaussian", gaussianBox);
        gaussianPane.setCollapsible(false);

        return gaussianPane;
    }

    @Override
    public TitledPane createExponentialPane() {
        exponentialCheckBox = new CheckBox("Exponential");
        exponentialCheckBox.setDisable(true);
        Tooltip exponentialTooltip = new Tooltip(
                "Select this option to generate data with an exponential distribution.\n" +
                        "An exponential distribution is often used to model the time between independent events that occur at a constant average rate.\n" +
                        "This type of data distribution is useful for evaluating the performance of hashing algorithms under\n" +
                        "conditions where data points have a higher probability of being close to the origin, but with a decreasing probability of larger values.\n" +
                        "Specify the lambda (rate) value to control the spread and frequency of occurrences within the distribution."
        );
        exponentialTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(exponentialCheckBox, exponentialTooltip);

        lambdaField = new TextField();
        lambdaField.setDisable(true);
        lambdaField.setPromptText("Lambda");
        Tooltip lambdaTooltip = new Tooltip(
                "Enter the lambda (rate) value for the exponential distribution.\n" +
                        "Lambda must be a positive floating-point number, representing the rate of occurrences within the distribution.\n" +
                        "A higher lambda value results in more data points being clustered near the beginning of the distribution, indicating a higher rate of occurrence."
        );
        lambdaTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(lambdaField, lambdaTooltip);

        generateExponentialDataButton = new Button("Generate Sample Exponential Data");
        generateExponentialDataButton.setDisable(true);
        Tooltip generateExponentialDataTooltip = new Tooltip(
                "Click this button to generate a sample dataset with an exponential distribution.\n" +
                        "The generated data will be based on the specified lambda (rate) value, and will be visualized on a chart to help you understand the distribution pattern."
        );
        generateExponentialDataTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(generateExponentialDataButton, generateExponentialDataTooltip);

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
        Tooltip fileChooserTooltip = new Tooltip(
                "Click this button to open a file explorer window and select a file from which data will be loaded.\n" +
                        "Only text-based file formats are supported (e.g., .txt, .csv)."
        );
        fileChooserTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(fileChooserButton, fileChooserTooltip);

        loadDataRadio = new RadioButton("Load from file");
        loadDataRadio.setToggleGroup(dataToggleGroup);
        Tooltip loadDataTooltip = new Tooltip(
                "Select this option to load data from an external file.\n" +
                        "By choosing this option, you can use custom datasets stored in a file, which allows for more flexible testing scenarios."
        );
        loadDataTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(loadDataRadio, loadDataTooltip);

        dataLoadingTimingSwitch = new ToggleSwitch("Load data during test addition: ");
        dataLoadingTimingSwitch.setDisable(true);
        Tooltip dataLoadingTimingTooltip = new Tooltip(
                "Choose when to load the data from the file.\n" +
                        "If enabled, the data will be loaded when adding the test to the list, ensuring that the\n" +
                        "same data is used every time the test is run, providing consistent results.\n" +
                        "If disabled, data will be loaded each time the test is executed, allowing for variability,\n" +
                        "which might be useful for evaluating performance under slightly different conditions each time."
        );
        dataLoadingTimingTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(dataLoadingTimingSwitch, dataLoadingTimingTooltip);

        VBox dataSourceBox = new VBox(5, loadDataRadio, fileChooserButton, filePathField, dataLoadingTimingSwitch);
        TitledPane dataSourcePane = new TitledPane("Data source", dataSourceBox);
        dataSourcePane.setCollapsible(false);

        return dataSourcePane;
    }

    @Override
    public TitledPane createBenchmarkParamsPane() {
        benchmarkIterationsField = new TextField();
        benchmarkIterationsField.setPromptText("Benchmark iterations");
        Tooltip benchmarkIterationsTooltip = new Tooltip(
                "Enter the number of iterations for the benchmark.\n" +
                        "This value must be a positive integer greater than 0.\n" +
                        "The benchmark calculates the factorial of 20 multiple times to gather reliable performance metrics.\n" +
                        "More iterations result in more accurate benchmarking but take longer to complete.\n" +
                        "A recommended number of iterations is between 100 and 1000, depending on the desired balance between precision and test duration."
        );
        benchmarkIterationsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(benchmarkIterationsField, benchmarkIterationsTooltip);

        benchmarkThresholdField = new TextField();
        benchmarkThresholdField.setPromptText("Benchmark threshold");
        Tooltip benchmarkThresholdTooltip = new Tooltip(
                "Enter the benchmark threshold value.\n" +
                        "Must be a positive floating-point number.\n" +
                        "If the difference between successive iteration averages falls below this value, benchmarking will stop early.\n" +
                        "Recommended values are between 0.001 and 0.01 for a good balance between precision and efficiency.\n" +
                        "Entering a negative number will result in the execution of all iterations."
        );
        benchmarkThresholdTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(benchmarkThresholdField, benchmarkThresholdTooltip);

        VBox benchmarkParamsBox = new VBox(5, benchmarkIterationsField, benchmarkThresholdField);
        benchmarkParamsPane = new TitledPane("Benchmark parameters", benchmarkParamsBox);
        benchmarkParamsPane.setCollapsible(false);

        return benchmarkParamsPane;
    }

    @Override
    public TitledPane createAdditionalSettingsPane() {
        testIterationsField = new TextField();
        testIterationsField.setPromptText("Test iterations");
        Tooltip testIterationsTooltip = new Tooltip(
                "Enter the number of iterations for the performance test.\n" +
                        "This value must be a positive integer greater than 0.\n" +
                        "The test is repeated multiple times to ensure that the results are reliable and not influenced by transient system conditions.\n" +
                        "More iterations generally yield more accurate results, but they also take longer to execute.\n" +
                        "A recommended number of iterations is between 50 and 500, depending on the desired balance between precision and the time taken for the test."
        );
        testIterationsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(testIterationsField, testIterationsTooltip);

        testThresholdField = new TextField();
        testThresholdField.setPromptText("Test Threshold");
        Tooltip testThresholdTooltip = new Tooltip(
                "Enter the threshold for ending test iterations early.\n" +
                        "This value must be a positive floating-point number.\n" +
                        "If the difference between consecutive iteration averages falls below this threshold, testing will end early to save time.\n" +
                        "Recommended values are between 0.001 and 0.01 for a good balance between precision and efficiency.\n" +
                        "Entering a negative number will result in the execution of all iterations."
        );
        testThresholdTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(testThresholdField, testThresholdTooltip);

        warmupIterationsField = new TextField();
        warmupIterationsField.setPromptText("Warmup iterations");
        Tooltip warmupIterationsTooltip = new Tooltip(
                "Enter the number of warmup iterations.\n" +
                        "Must be a positive integer.\n" +
                        "Warmup iterations are used to stabilize the system and minimize the impact of JVM optimizations, ensuring more reliable test results.\n" +
                        "A recommended number is between 10 and 50 to properly prepare for performance testing."
        );
        warmupIterationsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(warmupIterationsField, warmupIterationsTooltip);

        VBox testParamsBox = new VBox(5, testIterationsField, testThresholdField, warmupIterationsField);
        additionalSettingsPane = new TitledPane("Additional Settings", testParamsBox);
        additionalSettingsPane.setCollapsible(false);

        return additionalSettingsPane;
    }

    @Override
    public Button createRunTestButton() {
        runTestButton = new Button("Run the selected tests");
        Tooltip runTestTooltip = new Tooltip(
                "Click this button to execute all the selected tests.\n" +
                        "The tests configured with the specified parameters will run sequentially, and performance metrics will be collected for analysis."
        );
        runTestTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(runTestButton, runTestTooltip);
        return runTestButton;
    }

    @Override
    public Button createAddTestButton() {
        addTestButton = new Button("Add a test");
        Tooltip addTestTooltip = new Tooltip(
                "Click this button to add a test with the currently configured parameters.\n" +
                        "The test will be saved to the list and can be run later"
        );
        addTestTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(addTestButton, addTestTooltip);
        return addTestButton;
    }

    @Override
    public Button createRemoveTestButton() {
        removeTestButton = new Button("Delete the selected tests");
        Tooltip removeTestTooltip = new Tooltip(
                "Click this button to delete the selected tests from the list.\n" +
                        "Ensure you have selected the tests you wish to remove, as this action cannot be undone."
        );
        removeTestTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(removeTestButton, removeTestTooltip);
        return removeTestButton;
    }

    @Override
    public Button createExportSelectedTestsButton() {
        exportSelectedTestsButton = new Button("Export Selected Tests");
        Tooltip exportSelectedTestsTooltip = new Tooltip(
                "Click this button to export the selected tests to a JSON file.\n" +
                        "This allows you to save the test configurations for future use or share them with others."
        );
        exportSelectedTestsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(exportSelectedTestsButton, exportSelectedTestsTooltip);
        return exportSelectedTestsButton;
    }

    @Override
    public Button createImportTestsButton() {
        importTestsButton = new Button("Import Tests");
        Tooltip importTestsTooltip = new Tooltip(
                "Click this button to import test configurations from a JSON file.\n" +
                        "This allows you to load previously saved tests or configurations shared by others."
        );
        importTestsTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(importTestsButton, importTestsTooltip);
        return importTestsButton;
    }

    @Override
    public Button createLoadCSVFileButton(){
        loadCSVFileButton = new Button("Load Results");
        Tooltip loadCSVFileTooltip = new Tooltip(
                "Click this button to load performance results from a CSV file.\n" +
                        "This allows you to review and analyze results obtained previously in the testing environment."
        );
        Tooltip.install(loadCSVFileButton, loadCSVFileTooltip);
        loadCSVFileTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        return loadCSVFileButton;
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
        Tooltip testCheckListTooltip = new Tooltip(
                "This is the list of configured tests.\n" +
                        "You can select multiple tests to run, delete, or export.\n" +
                        "Double-click on a test to view its detailed configuration."
        );
        testCheckListTooltip.setShowDuration(javafx.util.Duration.seconds(60));
        Tooltip.install(testCheckListView, testCheckListTooltip);

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

    public Button getLoadCSVFileButton(){
        return loadCSVFileButton;
    }

    public CheckListView<HashTestConfig> getTestCheckListView() {
        return testCheckListView;
    }

}
