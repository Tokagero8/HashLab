module hashlab {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires javafx.swing;
    requires org.jfree.jfreechart;


    opens hashlab.core to javafx.fxml;
    exports hashlab.core;

    exports hashlab.tests;
    opens hashlab.tests to javafx.fxml;
    exports hashlab.ui.app;
    opens hashlab.ui.app to javafx.fxml;
    exports hashlab.ui.components;
    opens hashlab.ui.components to javafx.fxml;
}