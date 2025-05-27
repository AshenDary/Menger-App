package com.example.controller;

import com.example.InitializableWithData;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController implements InitializableWithData {

    @FXML private ListView<String> messageList;
    @FXML private TextField inputField;

    private ChatClient chatClient;

    @Override
    public void init(Object data) {
        System.out.println("Initializing ChatController with data: " + data);
        String username = (String) data;
        try {
            chatClient = new ChatClient(username, msg -> messageList.getItems().add(msg));
        } catch (Exception e) {
            e.printStackTrace();
            messageList.getItems().add(" Failed to connect.");
        }
    }

    @FXML
    private void sendMessage() {
        String msg = inputField.getText();
        if (!msg.isBlank()) {
            chatClient.sendMessage(msg);
            inputField.clear();
        }
    }
}
