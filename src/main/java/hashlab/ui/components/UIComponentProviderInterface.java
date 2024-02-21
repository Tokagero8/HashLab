package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.scene.control.*;
import org.controlsfx.control.CheckListView;

public interface UIComponentProviderInterface {
    ComboBox<String> getAlgorithmChoice();
    TextField getHashTableSizeField();
    TextField getChunkSizeField();
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
    Button getGenerateUniformDataButton();
    TitledPane getUniformPane();
    CheckBox getGaussianCheckBox();
    TextField getMeanField();
    TextField getDeviationField();
    Button getGenerateGaussianDataButton();
    TitledPane getGaussianPane();
    CheckBox getExponentialCheckBox();
    TextField getLambdaField();
    Button getGenerateExponentialDataButton();
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
