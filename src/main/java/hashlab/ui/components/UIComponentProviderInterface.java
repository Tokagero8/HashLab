package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.scene.control.*;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;

public interface UIComponentProviderInterface {
    ComboBox<String> getLanguageComboBox();
    TitledPane getLanguagePane();
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
    ToggleSwitch getDataGenerationTimingSwitch();
    TitledPane getGenerateDataPane();
    TitledPane getDataGenerationPane();
    CheckBox getUniformCheckBox();
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
    ToggleSwitch getDataLoadingTimingSwitch();
    TitledPane getDataSourcePane();
    TextField getBenchmarkIterationsField();
    TextField getBenchmarkThresholdField();
    TitledPane getBenchmarkParamsPane();
    TextField getTestIterationsField();
    TextField getTestThresholdField();
    TextField getWarmupIterationsField();
    TitledPane getAdditionalSettingsPane();
    Button getRunTestButton();
    Button getAddTestButton();
    Button getRemoveTestButton();
    Button getExportSelectedTestsButton();
    Button getImportTestsButton();
    Button getLoadCSVFileButton();
    Label getTestsListLabel();
    CheckListView<HashTestConfig> getTestCheckListView();
}
