package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainClient extends Application {
    
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 390, 844);
        stage.setScene(scene);
    
        String css = this.getClass().getResource("/view/meneger.css").toExternalForm();
        scene.getStylesheets().add(css);
    
        stage.setTitle("Menger");
        stage.getIcons().add(new Image(getClass().getResource("/assets/images/app_icon.png").toExternalForm()));
        stage.setResizable(false);
        stage.centerOnScreen();
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
            e.printStackTrace();s
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
