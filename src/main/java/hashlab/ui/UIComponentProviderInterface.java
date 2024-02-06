package hashlab.ui;

import hashlab.core.HashTestConfig;
import javafx.scene.control.*;
import org.controlsfx.control.CheckListView;

import java.io.File;

public interface UIComponentProviderInterface {
    ComboBox<String> getAlgorithmChoice();
    TextField getHashTableSizeField();
    TitledPane getHashAlgorithmsPane();
    CheckListView<String> getHashFunctionChoice();
    TitledPane getHashFunctionsPane();
    CheckBox getPutCheckbox();
    CheckBox getGetCheckbox();
    CheckBox getDeleteCheckbox();
    TitledPane getTestOperationsPane();
    ToggleGroup getDataToggleGroup();
    RadioButton getGenerateDataRadio();
    TextField getDataSizeField();
    TitledPane getGenerateDataPane();
    TitledPane getDataGenerationPane();
    CheckBox getUniformCheckBox();
    TextField getMinField();
    TextField getMaxField();
    TitledPane getUniformPane();
    CheckBox getGaussianCheckBox();
    TextField getMeanField();
    TextField getDeviationField();
    TitledPane getGaussianPane();
    CheckBox getExponentialCheckBox();
    TextField getLambdaField();
    TitledPane getExponentialPane();
    TextField getFilePathField();
    Button getFileChooserButton();
    RadioButton getLoadDataRadio();
    TextField getBenchmarkIterationsField();
    TextField getBenchmarkThresholdField();
    TitledPane getBenchmarkParamsPane();
    Button getRunTestButton();
    Button getAddTestButton();
    Button getRemoveTestButton();
    Button getExportSelectedTestsButton();
    Button getImportTestsButton();
    CheckListView<HashTestConfig> getTestCheckListView();
}
