package com.example;

import java.io.IOException;
import java.net.Socket;

import com.example.controller.BugoyChatBoxController;
import com.example.controller.ChatController;
import com.example.model.Chat;
import com.example.model.ClientSocket;
import com.example.model.CurrentUser;
import com.example.model.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainClient extends Application {
    
    private static Scene scene;
    public static ClientSocket clientSocket;

    @Override
    public void start(Stage stage) {
        try {
            if (CurrentUser.getInstance().getUser() == null) {
                scene = new Scene(loadFXML("login"), 520, 964);
            } else {
                String username = CurrentUser.getInstance().getUser().getUsername();
                try {
                    clientSocket = new ClientSocket(new Socket("localhost", 12345), username);
                    System.out.println("ClientSocket initialized: " + (clientSocket != null));
                } catch (IOException e) {
                    System.err.println("Failed to initialize ClientSocket: " + e.getMessage());
                    e.printStackTrace();
                }
                scene = new Scene(loadFXML("chat"), 520, 964);
            }

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
    
            if (controller instanceof BugoyChatBoxController) {
                BugoyChatBoxController chatController = (BugoyChatBoxController) controller;
                chatController.setClientSocket(clientSocket);
                if (controllerData instanceof Chat) {
                    chatController.setChat((Chat) controllerData);
                }
            } else if (controller instanceof ChatController && controllerData instanceof User) {
                ChatController chatController = (ChatController) controller;
                chatController.init(controllerData);
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
