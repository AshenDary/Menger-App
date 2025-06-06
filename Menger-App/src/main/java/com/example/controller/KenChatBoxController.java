package com.example.controller;

import java.io.IOException;

import com.example.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class KenChatBoxController {

    
    @FXML
    private ScrollPane chatareacontainer;
    
    @FXML
    public void initialize() {
        Platform.runLater(() -> chatareacontainer.setVvalue(1.0));
    }      

    @FXML
    private ImageView backicon;

    @FXML
    private void handleBackToChat() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

