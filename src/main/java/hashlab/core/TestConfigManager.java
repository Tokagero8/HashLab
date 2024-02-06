package hashlab.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hashlab.ui.HashLabAppController;
import hashlab.ui.NewHashLabAppController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestConfigManager {

    NewHashLabAppController controller;

    public void initialize(NewHashLabAppController controller){
        this.controller = controller;
    }

    public void importTestsAndAdd(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type testType = new TypeToken<ArrayList<HashTestConfig>>() {}.getType();
                List<HashTestConfig> importedTests = gson.fromJson(reader, testType);
                controller.addAllTests(importedTests);
                controller.updateTestListView();
            } catch (IOException e) {
                controller.showAlert("Error", "Failed to import tests: " + e.getMessage());
            }
        }
    }

    public void exportSelectedTests(Stage primaryStage, List<HashTestConfig> selectedTests) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("selected_tests.json");
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {

            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(selectedTests, writer);
            } catch (IOException e) {
                controller.showAlert("Error", "Failed to export tests: " + e.getMessage());
            }
        }
    }


}
