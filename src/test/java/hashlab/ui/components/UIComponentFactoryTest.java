package hashlab.ui.components;

import static org.junit.jupiter.api.Assertions.*;

import hashlab.tests.HashTestConfig;
import javafx.application.Platform;
import javafx.scene.control.*;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UIComponentFactoryTest {

    @BeforeAll
    public static void setUpClass() {
        Platform.startup(() -> {});
    }

    @Test
    void testCreateHashAlgorithmsPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createHashAlgorithmsPane();
        assertEquals("Hash algorithms", pane.getText(), "Pane title should be 'Hash algorithms'");
        TextField hashTableSizeField = factory.getHashTableSizeField();
        TextField chunkSizeField = factory.getChunkSizeField();
        assertEquals("", hashTableSizeField.getText(), "Hash table size field should be initially empty.");
        assertEquals("", chunkSizeField.getText(), "Chunk size field should be initially empty.");
    }

    @Test
    void testCreateHashFunctionsPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createHashFunctionsPane();
        assertEquals("Hash functions", pane.getText(), "Pane title should be 'Hash functions'");
    }

    @Test
    void testCreateTestOperationsPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createTestOperationsPane();
        assertEquals("Test operations", pane.getText(), "Pane title should be 'Test operations'");
        CheckBox putCheckbox = factory.getPutCheckbox();
        CheckBox getCheckbox = factory.getGetCheckbox();
        CheckBox deleteCheckbox = factory.getDeleteCheckbox();
        assertFalse(putCheckbox.isSelected(), "Put checkbox should be initially not selected.");
        assertFalse(getCheckbox.isSelected(), "Get checkbox should be initially not selected.");
        assertFalse(deleteCheckbox.isSelected(), "Delete checkbox should be initially not selected.");
    }

    @Test
    void testCreateDataGenerationPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createDataGenerationPane();
        assertEquals("Data Generation Methods", pane.getText(), "Pane title should be 'Data Generation Methods'");
        TextField dataSizeField = factory.getDataSizeField();
        assertEquals("", dataSizeField.getText(), "Data size field should be initially empty.");
        assertTrue(dataSizeField.isDisabled(), "Data size field should be initially disabled.");
    }

    @Test
    void testCreateUniformPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createUniformPane();
        assertEquals("Uniform", pane.getText(), "Pane title should be 'Uniform'");
        CheckBox uniformCheckBox = factory.getUniformCheckBox();
        assertFalse(uniformCheckBox.isSelected(), "Uniform checkbox should be initially not selected.");
        assertTrue(uniformCheckBox.isDisabled(), "Uniform checkbox should be initially disabled.");
    }

    @Test
    void testCreateGaussianPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createGaussianPane();
        assertEquals("Gaussian", pane.getText(), "Pane title should be 'Gaussian'");
        TextField meanField = factory.getMeanField();
        TextField deviationField = factory.getDeviationField();
        assertEquals("", meanField.getText(), "Mean field should be initially empty.");
        assertTrue(meanField.isDisabled(), "Mean field should be initially disabled.");
        assertEquals("", deviationField.getText(), "Deviation field should be initially empty.");
        assertTrue(deviationField.isDisabled(), "Deviation field should be initially disabled.");
    }

    @Test
    void testCreateExponentialPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createExponentialPane();
        assertEquals("Exponential", pane.getText(), "Pane title should be 'Exponential'");
        TextField lambdaField = factory.getLambdaField();
        assertEquals("", lambdaField.getText(), "Lambda field should be initially empty.");
        assertTrue(lambdaField.isDisabled(), "Lambda field should be initially disabled.");
    }

    @Test
    void testCreateDataSourcePane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createDataSourcePane(null);
        assertEquals("Data source", pane.getText(), "Pane title should be 'Data source'");
        TextField filePathField = factory.getFilePathField();
        assertEquals("", filePathField.getText(), "File path field should be initially empty.");
        assertTrue(filePathField.isDisabled(), "File path field should be initially disabled.");
    }

    @Test
    void testCreateBenchmarkParamsPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createBenchmarkParamsPane();
        assertEquals("Benchmark parameters", pane.getText(), "Pane title should be 'Benchmark parameters'");
        TextField benchmarkIterationsField = factory.getBenchmarkIterationsField();
        TextField benchmarkThresholdField = factory.getBenchmarkThresholdField();
        assertEquals("", benchmarkIterationsField.getText(), "Benchmark iterations field should be initially empty.");
        assertEquals("", benchmarkThresholdField.getText(), "Benchmark threshold field should be initially empty.");
    }

    @Test
    void testCreateAdditionalSettingsPane() {
        UIComponentFactory factory = new UIComponentFactory();
        TitledPane pane = factory.createAdditionalSettingsPane();
        assertEquals("Additional Settings", pane.getText(), "Pane title should be 'Additional Settings'");
        TextField testIterationsField = factory.getTestIterationsField();
        TextField testThresholdField = factory.getTestThresholdField();
        TextField warmupIterationsField = factory.getWarmupIterationsField();
        assertEquals("", testIterationsField.getText(), "Test iterations field should be initially empty.");
        assertEquals("", testThresholdField.getText(), "Test threshold field should be initially empty.");
        assertEquals("", warmupIterationsField.getText(), "Warmup iterations field should be initially empty.");
    }

    @Test
    void testButtonCreation() {
        UIComponentFactory factory = new UIComponentFactory();
        assertEquals("Run the selected tests", factory.createRunTestButton().getText(), "Button text should be 'Run the selected tests'");
        assertEquals("Add a test", factory.createAddTestButton().getText(), "Button text should be 'Add a test'");
        assertEquals("Delete the selected tests", factory.createRemoveTestButton().getText(), "Button text should be 'Delete the selected tests'");
        assertEquals("Export Selected Tests", factory.createExportSelectedTestsButton().getText(), "Button text should be 'Export Selected Tests'");
        assertEquals("Import Tests", factory.createImportTestsButton().getText(), "Button text should be 'Import Tests'");
        assertEquals("Load Results", factory.createLoadCSVFileButton().getText(), "Button text should be 'Load Results'");
    }

    @Test
    void testCreateTestCheckListView() {
        UIComponentFactory factory = new UIComponentFactory();
        assertEquals(CheckListView.class, factory.createTestCheckListView().getClass(), "Should return an instance of CheckListView");
        CheckListView<HashTestConfig> testCheckListView = factory.getTestCheckListView();
        assertTrue(testCheckListView.getItems().isEmpty(), "Test check list view should be initially empty.");
    }

    @Test
    void testSetAndGetComboBoxValue() {
        UIComponentFactory factory = new UIComponentFactory();
        factory.createHashAlgorithmsPane();
        ComboBox<String> algorithmChoice = factory.getAlgorithmChoice();
        algorithmChoice.getItems().clear();
        algorithmChoice.getItems().addAll("SHA-256", "MD5");

        Platform.runLater(() -> {
            algorithmChoice.getSelectionModel().select("SHA-256");
            assertEquals("SHA-256", algorithmChoice.getSelectionModel().getSelectedItem(), "Selected item should be 'SHA-256'");
            algorithmChoice.getSelectionModel().select("MD5");
            assertEquals("MD5", algorithmChoice.getSelectionModel().getSelectedItem(), "Selected item should be 'MD5'");
            algorithmChoice.getSelectionModel().select(0);
            assertEquals("SHA-256", algorithmChoice.getSelectionModel().getSelectedItem(), "Selected item should be 'SHA-256' when selected by index 0");
        });
    }

    @Test
    void testSetAndGetTextFieldValue() {
        UIComponentFactory factory = new UIComponentFactory();
        factory.createHashAlgorithmsPane();
        TextField hashTableSizeField = factory.getHashTableSizeField();

        Platform.runLater(() -> {
            hashTableSizeField.setText("1024");
            assertEquals("1024", hashTableSizeField.getText(), "Hash table size field should contain '1024'");
            hashTableSizeField.setText("2048");
            assertEquals("2048", hashTableSizeField.getText(), "Hash table size field should contain '2048'");
            hashTableSizeField.setText("");
            assertEquals("", hashTableSizeField.getText(), "Hash table size field should be empty");
        });
    }

    @Test
    void testEnableDataSizeField() {
        UIComponentFactory factory = new UIComponentFactory();
        factory.createDataGenerationPane();

        TextField dataSizeField = factory.getDataSizeField();
        RadioButton generateDataRadio = factory.getGenerateDataRadio();

        Platform.runLater(() -> {
            assertTrue(dataSizeField.isDisabled(), "Data size field should be initially disabled.");
            generateDataRadio.setSelected(true);
            assertFalse(dataSizeField.isDisabled(), "Data size field should be enabled when generate data is selected.");
            generateDataRadio.setSelected(false);
            assertTrue(dataSizeField.isDisabled(), "Data size field should be disabled when generate data is deselected.");
        });
    }

    @Test
    void testToggleSwitchState() {
        UIComponentFactory factory = new UIComponentFactory();
        factory.createDataGenerationPane();

        ToggleSwitch dataGenerationTimingSwitch = factory.getDataGenerationTimingSwitch();
        RadioButton generateDataRadio = factory.getGenerateDataRadio();

        Platform.runLater(() -> {
            assertTrue(dataGenerationTimingSwitch.isDisabled(), "Data generation timing switch should be initially disabled.");
            generateDataRadio.setSelected(true);
            assertFalse(dataGenerationTimingSwitch.isDisabled(), "Data generation timing switch should be enabled when generate data is selected.");
            generateDataRadio.setSelected(false);
            assertTrue(dataGenerationTimingSwitch.isDisabled(), "Data generation timing switch should be disabled when generate data is deselected.");
        });
    }
}
