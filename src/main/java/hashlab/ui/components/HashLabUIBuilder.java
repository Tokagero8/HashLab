    package hashlab.ui.components;

    import javafx.geometry.Insets;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;


    public class HashLabUIBuilder {

        private UIComponentFactoryInterface uiComponentFactory;

        public HashLabUIBuilder(UIComponentFactoryInterface uiComponentFactory){
            this.uiComponentFactory = uiComponentFactory;

        }

        public void buildUI(Stage primaryStage){

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(5));
            layout.setSpacing(5);


            layout.getChildren().addAll(
                    uiComponentFactory.createHashAlgorithmsPane(),
                    uiComponentFactory.createHashFunctionsPane(),
                    uiComponentFactory.createTestOperationsPane(),
                    uiComponentFactory.createDataGenerationPane(),
                    uiComponentFactory.createDataSourcePane(primaryStage),
                    uiComponentFactory.createBenchmarkParamsPane(),
                    uiComponentFactory.createRunTestButton(),
                    uiComponentFactory.createAddTestButton(),
                    uiComponentFactory.createRemoveTestButton(),
                    uiComponentFactory.createExportSelectedTestsButton(),
                    uiComponentFactory.createImportTestsButton(),
                    uiComponentFactory.createTestCheckListView()
            );

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(layout);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            Scene scene = new Scene(scrollPane, 1600, 900);
            scene.getStylesheets().add(getClass().getResource("/hashlab/css/fresh-look.css").toExternalForm());
            primaryStage.setScene(scene);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Testing Hashing Algorithms");
            primaryStage.show();
        }

    }
