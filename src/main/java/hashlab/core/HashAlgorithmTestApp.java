package hashlab.core;

import hashlab.algorithms.BSTHash;
import hashlab.algorithms.HashAlgorithm;
import hashlab.algorithms.LinearProbingHash;
import hashlab.algorithms.SeparateChainingHash;
import hashlab.benchmark.Benchmark;
import hashlab.benchmark.HashAlgorithmPerformanceTest;
import hashlab.functions.HashFunction;
import hashlab.functions.MD5Hash;
import hashlab.functions.SHA1Hash;
import hashlab.functions.SHA256Hash;
import hashlab.utils.DataGenerator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HashAlgorithmTestApp extends Application {

    private File selectedFile;
    List<HashTestConfig> tests = new ArrayList<>();
    private CheckListView<String> testCheckListView = new CheckListView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5));
        VBox.setVgrow(testCheckListView, Priority.ALWAYS);
        layout.setSpacing(5);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);

        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("BST", "Linear Probing", "Separate Chaining");
        grid.add(new Label("Algorytm:"), 0, 0);
        grid.add(algorithmChoice, 1, 0);

        TextField hashTableSizeField = new TextField();
        hashTableSizeField.setPromptText("Hash table size");
        grid.add(new Label("Hash table size:"), 2, 0);
        grid.add(hashTableSizeField, 3, 0);

        HBox hashAlgorithms = new HBox(10, algorithmChoice, hashTableSizeField);
        TitledPane hashAlgorithmsPane = new TitledPane("Hash algorithms", hashAlgorithms);
        hashAlgorithmsPane.setCollapsible(false);


        CheckListView<String> hashFunctionChoice = new CheckListView<>();
        hashFunctionChoice.getItems().addAll("MD5", "SHA1", "SHA256");
        TitledPane hashFunctionsPane = new TitledPane("Hash functions", hashFunctionChoice);
        hashFunctionsPane.setCollapsible(false);

        CheckBox putCheckbox = new CheckBox("Put");
        CheckBox getCheckbox = new CheckBox("Get");
        CheckBox deleteCheckbox = new CheckBox("Delete");
        HBox testOperations = new HBox(10, putCheckbox, getCheckbox, deleteCheckbox);
        TitledPane testOperationsPane = new TitledPane("Test operations", testOperations);
        testOperationsPane.setCollapsible(false);

        ToggleGroup dataToggleGroup = new ToggleGroup();
        RadioButton generateDataRadio = new RadioButton("Generate data");
        generateDataRadio.setToggleGroup(dataToggleGroup);

        TextField dataSizeField = new TextField();
        dataSizeField.setPromptText("Data size");
        dataSizeField.setDisable(true);

        VBox dataSizeBox = new VBox(5, generateDataRadio, dataSizeField);
        TitledPane generateDataPane = new TitledPane("Data", dataSizeBox);
        generateDataPane.setCollapsible(false);


        RadioButton loadDataRadio = new RadioButton("Load from file");
        loadDataRadio.setToggleGroup(dataToggleGroup);

        CheckBox uniformCheckBox = new CheckBox("Uniform");
        uniformCheckBox.setDisable(true);
        CheckBox gaussianCheckBox = new CheckBox("Gaussian");
        gaussianCheckBox.setDisable(true);
        CheckBox exponentialCheckBox = new CheckBox("Exponential");
        exponentialCheckBox.setDisable(true);

        TextField minField = new TextField();
        minField.setDisable(true);
        minField.setPromptText("Min");
        TextField maxField = new TextField();
        maxField.setDisable(true);
        maxField.setPromptText("Max");
        TextField meanField = new TextField();
        meanField.setDisable(true);
        meanField.setPromptText("Mean");
        TextField deviationField = new TextField();
        deviationField.setDisable(true);
        deviationField.setPromptText("Deviation");
        TextField lambdaField = new TextField();
        lambdaField.setDisable(true);
        lambdaField.setPromptText("Lambda");

        TextField filePathField = new TextField();
        filePathField.setDisable(true);
        filePathField.setEditable(false);

        Button fileChooserButton = new Button("Choose a file");
        fileChooserButton.setDisable(true);
        fileChooserButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null){
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        generateDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean isGenerateDataSelected = newValue;
            dataSizeField.setDisable(!newValue);
            uniformCheckBox.setDisable(!isGenerateDataSelected);
            gaussianCheckBox.setDisable(!isGenerateDataSelected);
            exponentialCheckBox.setDisable(!isGenerateDataSelected);
            fileChooserButton.setDisable(isGenerateDataSelected);
            filePathField.setDisable(isGenerateDataSelected);
            minField.setDisable(!(isGenerateDataSelected && uniformCheckBox.isSelected()));
            maxField.setDisable(!(isGenerateDataSelected && uniformCheckBox.isSelected()));
            meanField.setDisable(!(isGenerateDataSelected && gaussianCheckBox.isSelected()));
            deviationField.setDisable(!(isGenerateDataSelected && gaussianCheckBox.isSelected()));
            lambdaField.setDisable(!(isGenerateDataSelected && exponentialCheckBox.isSelected()));
        });

        loadDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean isLoadDataSelected = newValue;
            fileChooserButton.setDisable(!isLoadDataSelected);
            filePathField.setDisable(!isLoadDataSelected);
            minField.setDisable(isLoadDataSelected);
            maxField.setDisable(isLoadDataSelected);
            meanField.setDisable(isLoadDataSelected);
            deviationField.setDisable(isLoadDataSelected);
            lambdaField.setDisable(isLoadDataSelected);
        });

        ChangeListener<Boolean> checkBoxListener = (observable, oldValue, newValue) -> {
            minField.setDisable(!(uniformCheckBox.isSelected()));
            maxField.setDisable(!(uniformCheckBox.isSelected()));
            meanField.setDisable(!(gaussianCheckBox.isSelected()));
            deviationField.setDisable(!(gaussianCheckBox.isSelected()));
            lambdaField.setDisable(!(exponentialCheckBox.isSelected()));
        };
        uniformCheckBox.selectedProperty().addListener(checkBoxListener);
        gaussianCheckBox.selectedProperty().addListener(checkBoxListener);
        exponentialCheckBox.selectedProperty().addListener(checkBoxListener);

        VBox dataSourceBox = new VBox(5, generateDataPane, loadDataRadio, fileChooserButton, filePathField);
        TitledPane dataSourcePane = new TitledPane("Data source", dataSourceBox);
        dataSourcePane.setCollapsible(false);

        VBox uniformBox = new VBox(5, uniformCheckBox, minField, maxField);
        TitledPane uniformPane = new TitledPane("Uniform", uniformBox);
        uniformPane.setCollapsible(false);

        VBox gaussianBox = new VBox(5, gaussianCheckBox, meanField, deviationField);
        TitledPane gaussianPane = new TitledPane("Gaussian", gaussianBox);
        gaussianPane.setCollapsible(false);

        VBox exponentialBox = new VBox(5, exponentialCheckBox, lambdaField);
        TitledPane exponentialPane = new TitledPane("Exponential", exponentialBox);
        exponentialPane.setCollapsible(false);

        VBox dataGenerationBox = new VBox(5, generateDataPane, uniformPane, gaussianPane, exponentialPane);
        TitledPane dataGenerationPane = new TitledPane("Data Generation Methods", dataGenerationBox);
        dataGenerationPane.setCollapsible(false);

        TextField benchmarkIterationsField = new TextField();
        benchmarkIterationsField.setPromptText("Benchmark iterations");

        TextField benchmarkThresholdField = new TextField();
        benchmarkThresholdField.setPromptText("Benchmark threshold");

        VBox benchmarkParamsBox = new VBox(5, benchmarkIterationsField, benchmarkThresholdField);
        TitledPane benchmarkParamsPane = new TitledPane("Benchmark parameters", benchmarkParamsBox);
        benchmarkParamsPane.setCollapsible(false);

        Button runTestButton = new Button("Run the selected tests");
        runTestButton.setOnAction(e -> runTests());

        Button addTestButton = new Button("Add a test");
        addTestButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("Test " + (tests.size() + 1));
            dialog.setTitle("Test name");
            dialog.setHeaderText("Enter a name for the new test:");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                HashTestConfig config = new HashTestConfig();

                config.testName = name;
                config.algorithm = algorithmChoice.getValue();
                config.hashFunctions = new ArrayList<>(hashFunctionChoice.getCheckModel().getCheckedItems());
                config.put = putCheckbox.isSelected();
                config.get = getCheckbox.isSelected();
                config.delete = deleteCheckbox.isSelected();
                config.isDataGenerated = generateDataRadio.isSelected();
                config.uniformSelected = uniformCheckBox.isSelected();
                config.gaussianSelected = gaussianCheckBox.isSelected();
                config.exponentialSelected = exponentialCheckBox.isSelected();

                try {
                    config.hashTableSize = Integer.parseInt(hashTableSizeField.getText());
                    if (config.isDataGenerated) {
                        config.dataSize = Integer.parseInt(dataSizeField.getText());
                    }
                    if(config.uniformSelected) {
                        config.min = Double.parseDouble(minField.getText());
                        config.max = Double.parseDouble(maxField.getText());
                    }
                    if(config.gaussianSelected) {
                        config.mean = Double.parseDouble(meanField.getText());
                        config.deviation = Double.parseDouble(deviationField.getText());
                    }
                    if(config.exponentialSelected) {
                        config.lambda = Double.parseDouble(lambdaField.getText());
                    }

                    config.benchmarkIterations = Integer.parseInt(benchmarkIterationsField.getText());
                    config.benchmarkThreshold = Double.parseDouble(benchmarkThresholdField.getText());

                    tests.add(config);

                    testCheckListView.setItems(FXCollections.observableArrayList(
                            tests.stream().map(test -> test.testName).collect(Collectors.toList())
                    ));
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter valid numerical values");
                }
            });
        });

        Button removeTestButton = new Button("Delete the selected tests");
        removeTestButton.setOnAction(e -> {
            ObservableList<String> selectedTests = testCheckListView.getCheckModel().getCheckedItems();
            tests.removeIf(test -> selectedTests.contains(test.testName));
            testCheckListView.getItems().removeIf(selectedTests::contains);
            testCheckListView.getCheckModel().clearChecks(); // Clear checks after removal
        });

        testCheckListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedTestName = testCheckListView.getSelectionModel().getSelectedItem();
                HashTestConfig selectedTest = tests.stream()
                        .filter(t -> t.testName.equals(selectedTestName))
                        .findFirst()
                        .orElse(null);

                if (selectedTest != null) {
                    showTestDetails(selectedTest);
                }
            }
        });


        layout.getChildren().addAll(
                hashAlgorithmsPane,
                hashFunctionsPane,
                testOperationsPane,
                dataGenerationPane,
                dataSourcePane,
                benchmarkParamsPane,
                runTestButton,
                addTestButton,
                removeTestButton,
                testCheckListView);

        layout.getChildren().add(grid);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(scrollPane, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing Hashing Algorithms");
        primaryStage.show();

    }

    private void showTestDetails(HashTestConfig test) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test details");
        alert.setHeaderText(test.testName);
        alert.setContentText(test.toString());

        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void runTests(){
        List<HashTestConfig> selectedTests = tests.stream()
                .filter(test -> testCheckListView.getCheckModel().isChecked(test.testName))
                .collect(Collectors.toList());

        String resultFilePath = "test_results.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath, true))){
            for(HashTestConfig testConfig : selectedTests) {
                List<HashAlgorithm<String, Integer>> algorithms = createHashAlgorithms(testConfig);
                List<Map.Entry<String, String[]>> testKeysSets;
                if (testConfig.isDataGenerated) {
                    testKeysSets = generateTestKeys(testConfig);

                } else {
                    testKeysSets = loadDataFromFile(selectedFile);
                }

                int totalKetsSetsSize = 0;
                for (Map.Entry<String, String[]> entry : testKeysSets) {
                    totalKetsSetsSize += entry.getValue().length;
                }
                Integer[] testValues = new Integer[totalKetsSetsSize];
                Arrays.fill(testValues, 1);

                double baseline = Benchmark.calculateBaseline(testConfig.benchmarkIterations, testConfig.benchmarkThreshold);

                for (HashAlgorithm<String, Integer> algorithm : algorithms) {
                    for (Map.Entry<String, String[]> entry : testKeysSets) {
                        String dataType = entry.getKey();
                        String[] testKeysSet = entry.getValue();
                        HashAlgorithmPerformanceTest<String, Integer> performanceTest = new HashAlgorithmPerformanceTest<>(algorithm, testKeysSet, testValues);

                        if (testConfig.put) {
                            double result = performanceTest.runTest("put", baseline);
                            writer.write("Test: " + testConfig.testName + ", Algorithm: " + algorithm.getClass().getSimpleName() + ", Function: " + algorithm.getHashFunction().getClass().getSimpleName() + ", Data Type: " + dataType + " - PUT: " + result + "\n");
                        }
                        if (testConfig.get) {
                            double result = performanceTest.runTest("get", baseline);
                            writer.write("Test: " + testConfig.testName + ", Algorithm: " + algorithm.getClass().getSimpleName() + ", Function: " + algorithm.getHashFunction().getClass().getSimpleName() + ", Data Type: " + dataType + " - GET: " + result + "\n");
                        }
                        if (testConfig.delete) {
                            double result = performanceTest.runTest("delete", baseline);
                            writer.write("Test: " + testConfig.testName + ", Algorithm: " + algorithm.getClass().getSimpleName() + ", Function: " + algorithm.getHashFunction().getClass().getSimpleName() + ", Data Type: " + dataType + " - DELETE: " + result + "\n");
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<HashAlgorithm<String, Integer>> createHashAlgorithms(HashTestConfig testConfig) {
        List<HashAlgorithm<String, Integer>> algorithms = new ArrayList<>();
        for (String hashFunction : testConfig.hashFunctions) {
            HashAlgorithm<String, Integer> algorithm = HashAlgorithmFactory.createAlgorithm(testConfig.algorithm, hashFunction, testConfig.hashTableSize);
            algorithms.add(algorithm);
        }
        return algorithms;
    }

    private List<Map.Entry<String, String[]>> generateTestKeys(HashTestConfig testConfig) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();

        if (testConfig.uniformSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateUniformValues(testConfig.min, testConfig.max, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Uniform", keys));
        }
        if (testConfig.gaussianSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateGaussianValues(testConfig.mean, testConfig.deviation, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Gaussian", keys));
        }
        if (testConfig.exponentialSelected) {
            String[] keys = convertDoubleArrayToStringArray(DataGenerator.generateExponentialValues(testConfig.lambda, testConfig.dataSize));
            testKeysSets.add(new AbstractMap.SimpleEntry<>("Exponential", keys));
        }

        return testKeysSets;
    }

    private List<Map.Entry<String, String[]>> loadDataFromFile(File file) {
        List<Map.Entry<String, String[]>> testKeysSets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> keys = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                keys.add(line);
            }
            testKeysSets.add(new AbstractMap.SimpleEntry<>("FromFile", keys.toArray(new String[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testKeysSets;
    }

    private String[] convertDoubleArrayToStringArray(double[] doubleArray) {
        String[] stringArray = new String[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            stringArray[i] = String.valueOf(doubleArray[i]);
        }
        return stringArray;
    }


    public static void main(String[] args){
        launch(args);
    }
}
