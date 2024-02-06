package hashlab.core;

import hashlab.tests.TestTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressStage {

    Stage progressStage = new Stage();
    Label testLabel;

    public void showProgressStage(TestTask testTask){
        testLabel = new Label("Starting tests...");
        testLabel.setMinWidth(Label.USE_PREF_SIZE);
        testLabel.setPadding(new Insets(10, 0, 10, 0));

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.progressProperty().bind(testTask.progressProperty());

        progressIndicator.progressProperty().bind(testTask.progressProperty());

        Button cancelButton = new Button("Cancel");

        VBox progressBox = new VBox(10, testLabel ,progressIndicator, cancelButton);
        progressBox.setAlignment(Pos.CENTER);
        progressBox.setPadding(new Insets(20));

        Scene progressScene = new Scene(progressBox);

        progressStage.setScene(progressScene);
        progressStage.setTitle("Test Progress");
        progressStage.setResizable(false);
        progressStage.initModality(Modality.APPLICATION_MODAL);
        progressStage.setWidth(400);
        progressStage.setHeight(200);
        progressStage.show();

        cancelButton.setOnAction(e -> {
            testTask.cancel();
            progressStage.close();
        });
    }

    public void updateTestLabel(String newMessage){
        testLabel.setText(newMessage);
    }

    public void closeStage() {
        if (progressStage != null) {
            progressStage.close();
        }
    }
}
