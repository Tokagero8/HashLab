module com.example.hashlab {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hashlab to javafx.fxml;
    exports com.example.hashlab;
}