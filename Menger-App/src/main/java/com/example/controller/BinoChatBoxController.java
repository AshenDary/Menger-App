package com.example.controller;

import java.io.IOException;

import com.example.MainClient;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class BinoChatBoxController {

    @FXML
    private ImageView backicon;

    @FXML
    private void initialize() {
        // Add click event to backicon
        backicon.setOnMouseClicked(event -> {
            try {
                // Switch back to chat.fxml scene
                MainClient.setRoot("chat", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}