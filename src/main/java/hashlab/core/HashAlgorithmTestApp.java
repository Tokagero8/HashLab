package hashlab.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HashAlgorithmTestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(10);

        ComboBox<String> algorithmChoise = new ComboBox<>();
        algorithmChoise.getItems().addAll("BST", "LinearProbing", "Separate Chaining");

        ComboBox<String> hashFunctionChoice = new ComboBox<>();
        hashFunctionChoice.getItems().addAll("MD5", "SHA1", "SHA256");

        CheckBox putCheckbox = new CheckBox("Put");
        CheckBox getCheckbox = new CheckBox("Get");
        CheckBox deleteCheckbox = new CheckBox("Delete");

        ToggleGroup dataToggleGroup = new ToggleGroup();
        RadioButton generateDataRadio = new RadioButton("Generuj dane");
        generateDataRadio.setToggleGroup(dataToggleGroup);
        RadioButton loadDataRadio = new RadioButton("Wczytaj z pliku");
        loadDataRadio.setToggleGroup(dataToggleGroup);

        Button runTestButton = new Button("Uruchom testy");
        runTestButton.setOnAction(e -> runTests());

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        layout.getChildren().addAll(algorithmChoise,
                hashFunctionChoice,
                putCheckbox,
                getCheckbox,
                deleteCheckbox,
                generateDataRadio,
                loadDataRadio,
                runTestButton,
                resultArea);

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testowanie Algorytmów Haszujących");
        primaryStage.show();

    }

    public void runTests(){
        //TODO tests
    }

    public static void main(String[] args){
        launch(args);
    }
}
