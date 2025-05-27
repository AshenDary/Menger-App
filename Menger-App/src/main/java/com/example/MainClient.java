package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {
    
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Menger");
        stage.show();
    }

    public static void setRoot(String fxml, Object controllerData) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("/view/" + fxml + ".fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controllerData != null && controller instanceof InitializableWithData) {
                ((InitializableWithData) controller).init(controllerData);
            }
            scene.setRoot(root);
        } catch (IOException e) {
            throw new IOException("Failed to load FXML: " + fxml, e);
        }
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
