module com.example.hashlab {
    requires javafx.controls;
    requires javafx.fxml;


    opens hashlab.core to javafx.fxml;
    exports hashlab.core;
}