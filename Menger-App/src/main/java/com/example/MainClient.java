package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */

public class MainClient extends Application {
    
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String resourcePath = "/view/" + fxml + ".fxml";
        java.net.URL fxmlUrl = MainClient.class.getResource(resourcePath);
        System.out.println("Trying FXML: " + resourcePath + " -> " + fxmlUrl);
        if (fxmlUrl != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            return fxmlLoader.load();
        }
        throw new IOException("FXML file not found: " + resourcePath);
    }

    public static void main(String[] args) {
        launch();
    }

}
