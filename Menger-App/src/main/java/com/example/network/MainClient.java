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
            scene = new Scene(loadFXML("login"), 520, 964);
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource("/assets/images/mengericon.png").toExternalForm()));
            scene.getStylesheets().add(getClass().getResource("/view/meneger.css").toExternalForm());

            stage.setTitle("Menger");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeClientSocket() {
        User user = CurrentUser.getInstance().getUser();
        if (user == null) {
            System.err.println("Attempted to initialize ClientSocket with null user.");
            return;
        }

        try {
            clientSocket = new ClientSocket(user.getUsername(), message -> {
                Platform.runLater(() -> {
                    System.out.println("📩 Message received: " + message);
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

        if (controller instanceof BugoyChatBoxController && controllerData instanceof Chat) {
            BugoyChatBoxController chatController = (BugoyChatBoxController) controller;
            chatController.setClientSocket(clientSocket);
            chatController.setChat((Chat) controllerData);
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
