package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

public interface UIComponentFactoryInterface {
    TitledPane createLanguagePane();
    TitledPane createHashAlgorithmsPane();
    TitledPane createHashFunctionsPane();
    TitledPane createTestOperationsPane();
    TitledPane createDataGenerationPane();
    TitledPane createUniformPane();
    TitledPane createGaussianPane();
    TitledPane createExponentialPane();
    TitledPane createDataSourcePane(Stage primaryStage);
    TitledPane createBenchmarkParamsPane();
    TitledPane createAdditionalSettingsPane();
    Button createRunTestButton();
    Button createAddTestButton();
    Button createRemoveTestButton();
    Button createExportSelectedTestsButton();
    Button createImportTestsButton();
    Button createLoadCSVFileButton();
    CheckListView<HashTestConfig> createTestCheckListView();
}
