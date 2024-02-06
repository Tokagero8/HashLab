package hashlab.ui;

import hashlab.core.HashTestConfig;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

public interface UIComponentFactoryInterface {
    TitledPane createHashAlgorithmsPane();
    TitledPane createHashFunctionsPane();
    TitledPane createTestOperationsPane();
    TitledPane createDataGenerationPane();
    TitledPane createUniformPane();
    TitledPane createGaussianPane();
    TitledPane createExponentialPane();
    TitledPane createDataSourcePane(Stage primaryStage);
    TitledPane createBenchmarkParamsPane();
    Button createRunTestButton();
    Button createAddTestButton();
    Button createRemoveTestButton();
    Button createExportSelectedTestsButton();
    Button createImportTestsButton();
    CheckListView<HashTestConfig> createTestCheckListView();
}
