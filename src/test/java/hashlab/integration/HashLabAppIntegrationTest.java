package hashlab.integration;

import hashlab.tests.HashTestConfig;
import hashlab.ui.app.HashLabEventHandler;
import hashlab.ui.components.*;
import hashlab.utils.DataGenerator;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class HashLabAppIntegrationTest {

    private Stage primaryStage;
    private UIComponentFactory uiComponentFactory;
    private UIComponentProvider uiComponentProvider;
    private TestsList testsList;
    private HashLabEventHandler eventHandler;

    @BeforeAll
    static void initJFX() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            primaryStage = new Stage();
            uiComponentFactory = new UIComponentFactory();
            uiComponentProvider = new UIComponentProvider(uiComponentFactory);
            testsList = new TestsList();
            eventHandler = new HashLabEventHandler(uiComponentProvider, primaryStage, testsList);
        });
    }

    @AfterEach
    void tearDown() {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                primaryStage.close();
            }
        });
    }

    @Test
    void testPrimaryStageInitialization() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);

            assertNotNull(primaryStage, "Primary stage should be initialized");
        });
    }

    @Test
    void testUIComponentInitialization() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);

            assertNotNull(uiComponentFactory.getAlgorithmChoice(), "Algorithm choice should be initialized");
            assertNotNull(uiComponentFactory.getHashTableSizeField(), "Hash table size field should be initialized");
            assertNotNull(uiComponentFactory.getChunkSizeField(), "Chunk size field should be initialized");
            assertNotNull(uiComponentFactory.getHashFunctionChoice(), "Hash function choice should be initialized");
        });
    }

    @Test
    void testEventHandlers() {
        Platform.runLater(() -> {
            eventHandler.attachEventHandlers();

            uiComponentFactory.getGenerateDataRadio().setSelected(true);
            assertFalse(uiComponentFactory.getDataSizeField().isDisable(), "Data size field should be enabled when generate data is selected");
            uiComponentFactory.getLoadDataRadio().setSelected(true);
            assertFalse(uiComponentFactory.getFileChooserButton().isDisable(), "File chooser button should be enabled when load data is selected");
        });
    }

    @Test
    void testUIComponentProperties() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);

            assertEquals("", uiComponentFactory.getAlgorithmChoice().getValue(), "Algorithm choice should be empty by default");
            assertTrue(uiComponentFactory.getHashTableSizeField().getText().isEmpty(), "Hash table size field should be empty by default");
            assertTrue(uiComponentFactory.getChunkSizeField().getText().isEmpty(), "Chunk size field should be empty by default");
            assertFalse(uiComponentFactory.getGenerateDataRadio().isSelected(), "Generate data radio button should not be selected by default");
        });
    }

    @Test
    void testAddTest() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);
            eventHandler.attachEventHandlers();

            // Simulate user input
            uiComponentFactory.getAlgorithmChoice().setValue("BST");
            uiComponentFactory.getHashFunctionChoice().getCheckModel().check("MD5");
            uiComponentFactory.getPutCheckbox().setSelected(true);
            uiComponentFactory.getGenerateDataRadio().setSelected(true);
            uiComponentFactory.getDataSizeField().setText("100");
            uiComponentFactory.getBenchmarkIterationsField().setText("10");
            uiComponentFactory.getBenchmarkThresholdField().setText("0.1");
            uiComponentFactory.getTestIterationsField().setText("10");
            uiComponentFactory.getTestThresholdField().setText("0.1");
            uiComponentFactory.getWarmupIterationsField().setText("5");

            // Add test
            uiComponentFactory.getAddTestButton().fire();

            // Verify the test is added
            assertEquals(1, testsList.getTestsList().size(), "There should be one test in the tests list");
            HashTestConfig addedTest = testsList.getTestsList().get(0);
            assertEquals("BST", addedTest.getAlgorithm(), "Algorithm should be BST");
            assertTrue(addedTest.getHashFunctions().contains("MD5"), "Hash functions should contain MD5");
            assertTrue(addedTest.isPutSelected(), "PUT operation should be selected");
            assertTrue(addedTest.isDataGenerated(), "Data should be generated");
            assertEquals(100, addedTest.getDataSize(), "Data size should be 100");
        });
    }

    @Test
    void testDataGeneration() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);
            eventHandler.attachEventHandlers();

            // Simulate data generation action
            uiComponentFactory.getGenerateDataRadio().setSelected(true);
            uiComponentFactory.getUniformCheckBox().setSelected(true);
            uiComponentFactory.getDataSizeField().setText("50");

            // Generate uniform data
            uiComponentFactory.getGenerateUniformDataButton().fire();

            // Verify uniform data generation
            String sampleData = DataGenerator.generateUniformASCIIValue(50);
            assertNotNull(sampleData, "Sample data should not be null");
            assertEquals(50, sampleData.length(), "Sample data should have correct length");
        });
    }

    @Test
    void testFileSelection() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);
            eventHandler.attachEventHandlers();

            FileChooser fileChooser = new FileChooser();
            File file = new File("testdata.csv");
            uiComponentFactory.getFileChooserButton().fire();

            assertEquals(file.getAbsolutePath(), uiComponentFactory.getFilePathField().getText(), "File path should be set correctly");
        });
    }

    @Test
    void testRemoveTest() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);
            eventHandler.attachEventHandlers();

            uiComponentFactory.getAlgorithmChoice().setValue("BST");
            uiComponentFactory.getHashFunctionChoice().getCheckModel().check("MD5");
            uiComponentFactory.getPutCheckbox().setSelected(true);
            uiComponentFactory.getGenerateDataRadio().setSelected(true);
            uiComponentFactory.getDataSizeField().setText("100");
            uiComponentFactory.getAddTestButton().fire();

            assertEquals(1, testsList.getTestsList().size(), "There should be one test in the tests list");

            uiComponentFactory.getTestCheckListView().getCheckModel().check(0);
            uiComponentFactory.getRemoveTestButton().fire();

            assertEquals(0, testsList.getTestsList().size(), "There should be no tests in the tests list");
        });
    }

    @Test
    void testExportImportTests() {
        Platform.runLater(() -> {
            UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
            HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
            hashLabUIBuilder.buildUI(primaryStage);
            eventHandler.attachEventHandlers();

            uiComponentFactory.getAlgorithmChoice().setValue("BST");
            uiComponentFactory.getHashFunctionChoice().getCheckModel().check("MD5");
            uiComponentFactory.getPutCheckbox().setSelected(true);
            uiComponentFactory.getGenerateDataRadio().setSelected(true);
            uiComponentFactory.getDataSizeField().setText("100");
            uiComponentFactory.getAddTestButton().fire();

            uiComponentFactory.getExportSelectedTestsButton().fire();

            uiComponentFactory.getImportTestsButton().fire();

            assertEquals(1, testsList.getTestsList().size(), "There should be one test in the tests list after import");
            HashTestConfig importedTest = testsList.getTestsList().get(0);
            assertEquals("BST", importedTest.getAlgorithm(), "Algorithm should be BST");
            assertTrue(importedTest.getHashFunctions().contains("MD5"), "Hash functions should contain MD5");
            assertTrue(importedTest.isPutSelected(), "PUT operation should be selected");
        });
    }



}
