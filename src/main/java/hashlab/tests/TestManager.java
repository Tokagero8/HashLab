package hashlab.tests;

import hashlab.core.ProgressStage;
import hashlab.ui.app.NewHashLabAppController;

import java.util.*;

public class TestManager {

    NewHashLabAppController controller;
    ProgressStage progressStage;

    public void initialize(NewHashLabAppController controller){
        this.controller = controller;
    }

    public void runTests(String resultFileName, List<HashTestConfig> selectedTests){

        TestTask testTask = new TestTask(resultFileName, selectedTests);

        progressStage = new ProgressStage();
        progressStage.showProgressStage(testTask);

        controller.setButtonsDisabled(true);

        testTask.messageProperty().addListener((obs, oldMessage, newMessage) -> progressStage.updateTestLabel(newMessage));

        new Thread(testTask).start();

        testTask.setOnSucceeded(e -> {
            progressStage.closeStage();
            controller.setButtonsDisabled(false);
        });

        testTask.setOnCancelled(e -> {
            progressStage.closeStage();
            controller.setButtonsDisabled(false);
        });
    }

}
