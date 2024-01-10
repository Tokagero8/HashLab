package hashlab.core;

import hashlab.ui.HashLabAppController;

import java.io.*;
import java.util.*;

public class TestManager {

    HashLabAppController controller;
    ProgressStage progressStage;

    public void initialize(HashLabAppController controller){
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
