package hashlab.ui.components;

import hashlab.tests.HashTestConfig;
import javafx.scene.control.*;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;

public class UIComponentProvider implements UIComponentProviderInterface{

    UIComponentFactory uiComponentFactory;

    public UIComponentProvider(UIComponentFactory uiComponentFactory){
        this.uiComponentFactory = uiComponentFactory;
    }

    @Override
    public ComboBox<String> getAlgorithmChoice() {
        return uiComponentFactory.getAlgorithmChoice();
    }

    @Override
    public TextField getHashTableSizeField() {
        return uiComponentFactory.getHashTableSizeField();
    }

    @Override
    public TextField getChunkSizeField() {
        return uiComponentFactory.getChunkSizeField();
    }

    @Override
    public TitledPane getHashAlgorithmsPane() {
        return uiComponentFactory.getHashAlgorithmsPane();
    }

    @Override
    public CheckListView<String> getHashFunctionChoice() {
        return uiComponentFactory.getHashFunctionChoice();
    }

    @Override
    public TitledPane getHashFunctionsPane() {
        return uiComponentFactory.getHashFunctionsPane();
    }

    @Override
    public CheckBox getPutCheckbox() {
        return uiComponentFactory.getPutCheckbox();
    }

    @Override
    public CheckBox getGetCheckbox() {
        return uiComponentFactory.getGetCheckbox();
    }

    @Override
    public CheckBox getDeleteCheckbox() {
        return uiComponentFactory.getDeleteCheckbox();
    }

    @Override
    public TitledPane getTestOperationsPane() {
        return uiComponentFactory.getTestOperationsPane();
    }

    @Override
    public ToggleGroup getDataToggleGroup() {
        return uiComponentFactory.getDataToggleGroup();
    }

    @Override
    public RadioButton getGenerateDataRadio() {
        return uiComponentFactory.getGenerateDataRadio();
    }

    @Override
    public TextField getDataSizeField() {
        return uiComponentFactory.getDataSizeField();
    }

    @Override
    public ToggleSwitch getDataGenerationTimingSwitch() {
        return uiComponentFactory.getDataGenerationTimingSwitch();
    }

    @Override
    public TitledPane getGenerateDataPane() {
        return uiComponentFactory.getGenerateDataPane();
    }

    @Override
    public TitledPane getDataGenerationPane() {
        return uiComponentFactory.getDataGenerationPane();
    }

    @Override
    public CheckBox getUniformCheckBox() {
        return uiComponentFactory.getUniformCheckBox();
    }

    @Override
    public TextField getMinField() {
        return uiComponentFactory.getMinField();
    }

    @Override
    public TextField getMaxField() {
        return uiComponentFactory.getMaxField();
    }

    @Override
    public Button getGenerateUniformDataButton() {
        return uiComponentFactory.getGenerateUniformDataButton();
    }

    @Override
    public TitledPane getUniformPane() {
        return uiComponentFactory.getUniformPane();
    }

    @Override
    public CheckBox getGaussianCheckBox() {
        return uiComponentFactory.getGaussianCheckBox();
    }

    @Override
    public TextField getMeanField() {
        return uiComponentFactory.getMeanField();
    }

    @Override
    public TextField getDeviationField() {
        return uiComponentFactory.getDeviationField();
    }

    @Override
    public Button getGenerateGaussianDataButton() {
        return uiComponentFactory.getGenerateGaussianDataButton();
    }

    @Override
    public TitledPane getGaussianPane() {
        return uiComponentFactory.getGaussianPane();
    }

    @Override
    public CheckBox getExponentialCheckBox() {
        return uiComponentFactory.getExponentialCheckBox();
    }

    @Override
    public TextField getLambdaField() {
        return uiComponentFactory.getLambdaField();
    }

    @Override
    public Button getGenerateExponentialDataButton() {
        return uiComponentFactory.getGenerateExponentialDataButton();
    }

    @Override
    public TitledPane getExponentialPane() {
        return uiComponentFactory.getExponentialPane();
    }

    @Override
    public TextField getFilePathField() {
        return uiComponentFactory.getFilePathField();
    }

    @Override
    public Button getFileChooserButton() {
        return uiComponentFactory.getFileChooserButton();
    }

    @Override
    public RadioButton getLoadDataRadio() {
        return uiComponentFactory.getLoadDataRadio();
    }

    @Override
    public ToggleSwitch getDataLoadingTimingSwitch() {
        return uiComponentFactory.getDataLoadingTimingSwitch();
    }

    @Override
    public TextField getBenchmarkIterationsField() {
        return uiComponentFactory.getBenchmarkIterationsField();
    }

    @Override
    public TextField getBenchmarkThresholdField() {
        return uiComponentFactory.getBenchmarkThresholdField();
    }

    @Override
    public TitledPane getBenchmarkParamsPane() {
        return uiComponentFactory.getBenchmarkParamsPane();
    }

    @Override
    public TextField getTestIterationsField() {
        return uiComponentFactory.getTestIterationsField();
    }

    @Override
    public TextField getTestThresholdField() {
        return uiComponentFactory.getTestThresholdField();
    }

    @Override
    public TextField getWarmupIterationsField() {
        return uiComponentFactory.getWarmupIterationsField();
    }

    @Override
    public TitledPane getAdditionalSettingsPane() {
        return uiComponentFactory.getAdditionalSettingsPane();
    }

    @Override
    public Button getRunTestButton() {
        return uiComponentFactory.getRunTestButton();
    }

    @Override
    public Button getAddTestButton() {
        return uiComponentFactory.getAddTestButton();
    }

    @Override
    public Button getRemoveTestButton() {
        return uiComponentFactory.getRemoveTestButton();
    }

    @Override
    public Button getExportSelectedTestsButton() {
        return uiComponentFactory.getExportSelectedTestsButton();
    }

    @Override
    public Button getImportTestsButton() {
        return uiComponentFactory.getImportTestsButton();
    }

    @Override
    public CheckListView<HashTestConfig> getTestCheckListView() {
        return uiComponentFactory.getTestCheckListView();
    }


}
