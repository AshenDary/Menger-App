module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.websocket.client;
    requires jakarta.websocket;
    requires org.glassfish.tyrus.server;

    exports com.example.controller to javafx.fxml;
    opens com.example.controller to javafx.fxml;
    exports com.example.network to javafx.graphics;
}