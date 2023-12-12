module com.example.hashlab {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    opens hashlab.core to javafx.fxml;
    exports hashlab.core;
}