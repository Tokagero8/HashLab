    package hashlab.ui.components;

    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;


    public class HashLabUIBuilder {

        private UIComponentFactoryInterface uiComponentFactory;

        public HashLabUIBuilder(UIComponentFactoryInterface uiComponentFactory){
            this.uiComponentFactory = uiComponentFactory;

        }

        public void buildUI(Stage primaryStage){

            VBox mainLayout = new VBox(10);
            mainLayout.setPadding(new Insets(5));
            mainLayout.setSpacing(5);

            mainLayout.getChildren().addAll(
                    uiComponentFactory.createHashAlgorithmsPane(),
                    uiComponentFactory.createHashFunctionsPane(),
                    uiComponentFactory.createTestOperationsPane(),
                    uiComponentFactory.createDataGenerationPane(),
                    uiComponentFactory.createDataSourcePane(primaryStage),
                    uiComponentFactory.createBenchmarkParamsPane(),
                    uiComponentFactory.createAdditionalSettingsPane()
            );

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(mainLayout);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            VBox rightLayout = new VBox(10);
            rightLayout.setPadding(new Insets(5));
            rightLayout.setSpacing(10);
            rightLayout.setAlignment(Pos.TOP_CENTER);

            rightLayout.getChildren().add(uiComponentFactory.createLanguagePane());


            VBox buttonsLayout = new VBox(10);
            buttonsLayout.setPadding(new Insets(5));
            buttonsLayout.setSpacing(5);
            buttonsLayout.setAlignment(Pos.CENTER);
            buttonsLayout.getStyleClass().add("buttons-pane");

            buttonsLayout.getChildren().addAll(
                    uiComponentFactory.createRunTestButton(),
                    uiComponentFactory.createAddTestButton(),
                    uiComponentFactory.createRemoveTestButton(),
                    uiComponentFactory.createExportSelectedTestsButton(),
                    uiComponentFactory.createImportTestsButton(),
                    uiComponentFactory.createLoadCSVFileButton()
            );

            rightLayout.getChildren().add(buttonsLayout);


            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(5));
            listLayout.setSpacing(5);

            ListView<?> testCheckListView = uiComponentFactory.createTestCheckListView();
            testCheckListView.setPrefHeight(225);

            listLayout.getChildren().addAll(
                    uiComponentFactory.createTestsListLabel(),
                    testCheckListView
            );


            BorderPane mainPane = new BorderPane();
            mainPane.setCenter(scrollPane);
            mainPane.setRight(rightLayout);
            mainPane.setBottom(listLayout);

            Scene scene = new Scene(mainPane, 1600, 900);
            scene.getStylesheets().add(getClass().getResource("/hashlab/css/fresh-look.css").toExternalForm());
            primaryStage.setScene(scene);

            primaryStage.setTitle("HashLab");
            primaryStage.show();
        }

    }
