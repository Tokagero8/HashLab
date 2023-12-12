package hashlab.core;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HashAlgorithmTestApp extends Application {

    private File selectedFile;
    List<HashTestConfig> tests = new ArrayList<>();
    private ListView<String> testListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);

        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("BST", "Linear Probing", "Separate Chaining");
        grid.add(new Label("Algorytm:"), 0, 0);
        grid.add(algorithmChoice, 1, 0);

        ListView<String> hashFunctionChoice = new ListView<>();
        hashFunctionChoice.getItems().addAll("MD5", "SHA1", "SHA256");
        hashFunctionChoice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        CheckBox putCheckbox = new CheckBox("Put");
        CheckBox getCheckbox = new CheckBox("Get");
        CheckBox deleteCheckbox = new CheckBox("Delete");

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

        Button fileChooserButton = new Button("Wybierz plik");
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

        TextField benchmarkIterationsField = new TextField();
        benchmarkIterationsField.setPromptText("Benchmark iterations");

        TextField benchmarkThresholdField = new TextField();
        benchmarkThresholdField.setPromptText("Benchmark threshold");

        Button runTestButton = new Button("Uruchom testy");
        runTestButton.setOnAction(e -> runTests());

        Button addTestButton = new Button("Dodaj test");
        addTestButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("Test " + (tests.size() + 1));
            dialog.setTitle("Nazwa testu");
            dialog.setHeaderText("Wprowadź nazwę dla nowego testu:");
            dialog.setContentText("Nazwa:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                HashTestConfig config = new HashTestConfig();

                config.testName = name;
                config.algorithm = algorithmChoice.getValue();
                config.hashFunctions = new ArrayList<>(hashFunctionChoice.getSelectionModel().getSelectedItems());
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

                    testListView.getItems().add(name);
                } catch (NumberFormatException ex) {
                    showAlert("Błąd", "Proszę wprowadzić poprawne wartości liczbowe");
                }
            });
        });

        testListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedTestName = testListView.getSelectionModel().getSelectedItem();
                HashTestConfig selectedTest = tests.stream()
                        .filter(t -> t.testName.equals(selectedTestName))
                        .findFirst()
                        .orElse(null);

                if (selectedTest != null) {
                    showTestDetails(selectedTest);
                }
            }
        });


        layout.getChildren().addAll(algorithmChoice,
                hashFunctionChoice,
                putCheckbox,
                getCheckbox,
                deleteCheckbox,
                generateDataRadio,
                uniformCheckBox,
                minField,
                maxField,
                gaussianCheckBox,
                meanField,
                deviationField,
                exponentialCheckBox,
                lambdaField,
                loadDataRadio,
                fileChooserButton,
                benchmarkIterationsField,
                benchmarkThresholdField,
                runTestButton,
                addTestButton,
                testListView);

        layout.getChildren().add(grid);

        Scene scene = new Scene(layout, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testowanie Algorytmów Haszujących");
        primaryStage.show();

    }

    private void showTestDetails(HashTestConfig test) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Szczegóły testu");
        alert.setHeaderText(test.testName);
        alert.setContentText(test.toString()); // Załóżmy, że HashTestConfig ma metodę toString generującą szczegółowy opis

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
