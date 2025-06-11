package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class JaridChatBoxController {

    
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
    
