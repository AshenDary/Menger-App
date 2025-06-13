package com.example.network;

import java.io.IOException;

import com.example.controller.BugoyChatBoxController;
import com.example.controller.ChatController;
import com.example.model.Chat;
import com.example.model.CurrentUser;
import com.example.model.User;

import javafx.application.Application;
import javafx.application.Platform;
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
                // No user logged in, show login scene
                scene = new Scene(loadFXML("login"), 520, 964);
            } else {
                // User already logged in, now we can safely init socket
                initializeClientSocket();
                setRoot("chat", null);
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
            e.printStackTrace();
        }
    }

    public static void initializeClientSocket() {
        // Only call this if you're sure CurrentUser is not null!
        User user = CurrentUser.getInstance().getUser();
        if (user == null) {
            System.err.println("Attempted to initialize ClientSocket with null user.");
            return;
        }

        String username = user.getUsername();
        try {
            clientSocket = new ClientSocket(username, message -> {
                Platform.runLater(() -> {
                    System.out.println("📩 Message received: " + message);
                    // Here you can notify controllers later if needed
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml, Object controllerData) throws IOException {
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

        if (scene == null) {
            scene = new Scene(root, 520, 964);
        } else {
            scene.setRoot(root);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClient.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }

    public static void main(String[] args) {
        launch();
    }
}
