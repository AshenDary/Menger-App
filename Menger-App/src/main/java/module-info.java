module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.controller to javafx.fxml;
    exports com.example; // if you use MainClient elsewhere

    opens com.example.controller to javafx.fxml;
}
