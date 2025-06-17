package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.fxml.FXML;

public class FriendsController {

    @FXML
    private void HandleChatTransfer() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
