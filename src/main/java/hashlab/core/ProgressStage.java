package hashlab.core;

import hashlab.tests.TestTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressStage {

    Stage progressStage = new Stage();
    Label testLabel;

    public void showProgressStage(TestTask testTask){
        testLabel = new Label("Starting tests...");
        testLabel.setMinWidth(300);
        testLabel.setMaxWidth(300);
        testLabel.setPadding(new Insets(10, 0, 10, 0));
        testLabel.setAlignment(Pos.CENTER_LEFT);

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
        progressStage.setWidth(600);
        progressStage.setHeight(400);
        progressStage.show();

        cancelButton.setOnAction(e -> {
            testTask.cancel();
            progressStage.close();
        });
    }

    public void updateTestLabel(String newMessage){
        testLabel.setText(newMessage);
    }

    public void showCompletionDialog(Runnable onViewResult){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Test Completed");
        alert.setHeaderText("Tests completed successfully.");
        alert.setContentText("Do you want to view the results?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response ->{
            if(response == buttonTypeYes){
                onViewResult.run();
            }
        });
    }

    public void closeStage() {
        if (progressStage != null) {
            progressStage.close();
        }
    }
}
