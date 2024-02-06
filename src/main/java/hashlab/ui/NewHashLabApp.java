package hashlab.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class NewHashLabApp extends Application {


    @Override
    public void start(Stage primaryStage){
        UIComponentFactory uiComponentFactory = new UIComponentFactory();
        UIComponentFactoryInterface uiComponentFactoryInterface = uiComponentFactory;
        HashLabUIBuilder hashLabUIBuilder = new HashLabUIBuilder(uiComponentFactoryInterface);
        hashLabUIBuilder.buildUI(primaryStage);

        UIComponentProviderInterface uiComponentProviderInterface = new UIComponentProvider(uiComponentFactory);
        TestsListInterface tests = new TestsList();
        HashLabEventHandler eventHandler = new HashLabEventHandler(uiComponentProviderInterface, primaryStage, tests);
        NewHashLabAppController newHashLabAppController = new NewHashLabAppController();
        newHashLabAppController.initialize(eventHandler);
        eventHandler.attachEventHandlers();
    }

    public static void main(String[] args){
        launch(args);
    }
}
