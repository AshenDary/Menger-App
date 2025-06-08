package com.example;

import java.io.IOException;
import java.net.Socket;

import com.example.controller.BugoyChatBoxController;
import com.example.model.Chat;
import com.example.network.ClientSocket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainClient extends Application {
    
    private static Scene scene;
    private static ClientSocket clientSocket; 

    @Override
    public void start(Stage stage) {
        try {
            clientSocket = new ClientSocket(new Socket("localhost", 12345));
            scene = new Scene(loadFXML("login"), 520, 964);
            stage.setScene(scene);

            String css = this.getClass().getResource("/view/meneger.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Menger");
            stage.getIcons().add(new Image(getClass().getResource("/assets/images/mengericon.png").toExternalForm()));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml, Object controllerData) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("/view/" + fxml + ".fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controllerData != null && controller instanceof InitializableWithData) {
                ((InitializableWithData) controller).init(controllerData);
            } else if (controllerData instanceof Chat && controller instanceof BugoyChatBoxController) {
                ((BugoyChatBoxController) controller).setChat((Chat) controllerData);
            }
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }

    public static void main(String[] args) {
        launch();
    }

}
