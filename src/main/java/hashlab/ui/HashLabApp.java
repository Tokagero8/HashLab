package hashlab.ui;

import hashlab.core.HashTestConfig;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HashLabApp extends Application {

    private final HashLabAppController controller = new HashLabAppController();

    private File selectedFile;
    ObservableList<HashTestConfig> tests = FXCollections.observableArrayList();
    CheckListView<HashTestConfig> testCheckListView = new CheckListView<>();

    Button runTestButton;
    Button addTestButton;
    Button removeTestButton;
    Button exportSelectedTestsButton;
    Button importTestsButton;

    @Override
    public void start(Stage primaryStage) throws Exception {

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

        controller.initialize(this);


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

        runTestButton = new Button("Run the selected tests");
        runTestButton.setOnAction(e -> {
            TextInputDialog fileDialog = new TextInputDialog("results");
            fileDialog.setTitle("Result File Name");
            fileDialog.setHeaderText("Enter the name of the file to save results:");
            fileDialog.setContentText("File name:");

            Optional<String> resultFileName = fileDialog.showAndWait();

            List<HashTestConfig> selectedTests = new ArrayList<>(testCheckListView.getCheckModel().getCheckedItems());

            resultFileName. ifPresent(name -> controller.runTests(name, selectedFile, selectedTests));
        });

        addTestButton = new Button("Add a test");
        addTestButton.setOnAction(e -> {
            if (!controller.validateTestConfig(algorithmChoice, hashTableSizeField, hashFunctionChoice,
                    putCheckbox, getCheckbox, deleteCheckbox,
                    generateDataRadio, loadDataRadio, selectedFile, uniformCheckBox,
                    gaussianCheckBox, exponentialCheckBox, minField,
                    maxField, meanField, deviationField, lambdaField,
                    dataSizeField, benchmarkIterationsField, benchmarkThresholdField)) {
                return;
            }

            TextInputDialog dialog = new TextInputDialog("Test " + (tests.size() + 1));
            dialog.setTitle("Test name");
            dialog.setHeaderText("Enter a name for the new test:");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                HashTestConfig config = new HashTestConfig();

                config.setId(UUID.randomUUID().toString());
                config.setTestName(name);
                config.setAlgorithm(algorithmChoice.getValue());
                config.setHashFunctions(new ArrayList<>(hashFunctionChoice.getCheckModel().getCheckedItems()));
                config.setPut(putCheckbox.isSelected());
                config.setGet(getCheckbox.isSelected());
                config.setDelete(deleteCheckbox.isSelected());
                config.setDataGenerated(generateDataRadio.isSelected());
                config.setUniformSelected(uniformCheckBox.isSelected());
                config.setGaussianSelected(gaussianCheckBox.isSelected());
                config.setExponentialSelected(exponentialCheckBox.isSelected());

                try {
                    config.setHashTableSize(Integer.parseInt(hashTableSizeField.getText()));
                    if (config.isDataGenerated()) {
                        config.setDataSize(Integer.parseInt(dataSizeField.getText()));
                    }
                    if(config.isUniformSelected()) {
                        config.setMin(Double.parseDouble(minField.getText()));
                        config.setMax(Double.parseDouble(maxField.getText()));
                    }
                    if(config.isGaussianSelected()) {
                        config.setMean(Double.parseDouble(meanField.getText()));
                        config.setDeviation(Double.parseDouble(deviationField.getText()));
                    }
                    if(config.isExponentialSelected()) {
                        config.setLambda(Double.parseDouble(lambdaField.getText()));
                    }

                    config.setBenchmarkIterations(Integer.parseInt(benchmarkIterationsField.getText()));
                    config.setBenchmarkThreshold(Double.parseDouble(benchmarkThresholdField.getText()));

                    tests.add(config);
                    testCheckListView.setItems(FXCollections.observableArrayList(tests));
                } catch (NumberFormatException ex) {
                    controller.showAlert("Error", "Please enter valid numerical values");
                }
            });
        });

        removeTestButton = new Button("Delete the selected tests");
        removeTestButton.setOnAction(e -> {
            List<HashTestConfig> selectedTests = new ArrayList<>(testCheckListView.getCheckModel().getCheckedItems());
            tests.removeAll(selectedTests);
            testCheckListView.setItems(tests);
            testCheckListView.getCheckModel().clearChecks();
        });

        testCheckListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                HashTestConfig selectedTest = testCheckListView.getSelectionModel().getSelectedItem();
                if (selectedTest != null) {
                    controller.showTestDetails(selectedTest);
                }
            }
        });

        exportSelectedTestsButton = new Button("Export Selected Tests");
        exportSelectedTestsButton.setOnAction(e -> {
            List<HashTestConfig> selectedTests = new ArrayList<>(testCheckListView.getCheckModel().getCheckedItems());
            controller.exportSelectedTests(primaryStage, selectedTests);
        });

        importTestsButton = new Button("Import Tests");
        importTestsButton.setOnAction(e -> controller.importTestsAndAdd(primaryStage));


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
                exportSelectedTestsButton,
                importTestsButton,
                testCheckListView);

        layout.getChildren().add(grid);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(scrollPane, 600, 800);
        scene.getStylesheets().add(getClass().getResource("/hashlab/css/fresh-look.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing Hashing Algorithms");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
