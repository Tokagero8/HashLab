package hashlab.core;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        TitledPane hashAlgorithmsPane = new TitledPane("Hash algorithms", algorithmChoice);
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
        RadioButton generateDataRadio = new RadioButton("Generuj dane");
        generateDataRadio.setToggleGroup(dataToggleGroup);
        RadioButton loadDataRadio = new RadioButton("Wczytaj z pliku");
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

        Button fileChooserButton = new Button("Choose a file");
        fileChooserButton.setDisable(true);
        fileChooserButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(primaryStage);
        });

        generateDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean isGenerateDataSelected = newValue;
            uniformCheckBox.setDisable(!isGenerateDataSelected);
            gaussianCheckBox.setDisable(!isGenerateDataSelected);
            exponentialCheckBox.setDisable(!isGenerateDataSelected);
            fileChooserButton.setDisable(isGenerateDataSelected);
            minField.setDisable(!(isGenerateDataSelected && uniformCheckBox.isSelected()));
            maxField.setDisable(!(isGenerateDataSelected && uniformCheckBox.isSelected()));
            meanField.setDisable(!(isGenerateDataSelected && gaussianCheckBox.isSelected()));
            deviationField.setDisable(!(isGenerateDataSelected && gaussianCheckBox.isSelected()));
            lambdaField.setDisable(!(isGenerateDataSelected && exponentialCheckBox.isSelected()));
        });

        loadDataRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            boolean isLoadDataSelected = newValue;
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

        VBox dataSourceBox = new VBox(5, generateDataRadio, loadDataRadio, fileChooserButton);
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

        VBox dataGenerationBox = new VBox(5, generateDataRadio, uniformPane, gaussianPane, exponentialPane);
        TitledPane dataGenerationPane = new TitledPane("Data Generation Methods", dataGenerationBox);
        dataGenerationPane.setCollapsible(false);

        TextField benchmarkIterationsField = new TextField();
        benchmarkIterationsField.setPromptText("Benchmark iterations");

        TextField benchmarkThresholdField = new TextField();
        benchmarkThresholdField.setPromptText("Benchmark threshold");

        VBox benchmarkParamsBox = new VBox(5, benchmarkIterationsField, benchmarkThresholdField);
        TitledPane benchmarkParamsPane = new TitledPane("Benchmark parameters", benchmarkParamsBox);
        benchmarkParamsPane.setCollapsible(false);

        Button runTestButton = new Button("Run tests");
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
                dataGenerationBox,
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
        //TODO tests
    }

    public static void main(String[] args){
        launch(args);
    }
}
