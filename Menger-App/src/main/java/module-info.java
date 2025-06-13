module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.websocket.client;
    requires jakarta.websocket;
    requires org.glassfish.tyrus.server;
    requires org.glassfish.tyrus.client;
    requires org.glassfish.tyrus.container.grizzly.server;

    // Allow JavaFX to access FXML controller classes
    opens com.example.controller to javafx.fxml, javafx.graphics;
    exports com.example.controller;

    // Allow WebSocket server (Tyrus) and JavaFX to access network classes
    opens com.example.network to org.glassfish.tyrus.core, javafx.graphics, javafx.fxml;
    exports com.example.network;

    // Allow other packages to access model classes
    exports com.example.model;
    opens com.example.model to javafx.fxml;
}
