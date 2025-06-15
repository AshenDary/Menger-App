package com.example.controller;

import java.io.IOException;

import com.example.model.Chat;
import com.example.network.ClientSocket;
import com.example.network.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BugoyChatBoxController {

    @FXML
    private VBox chatpreviewcontainer;

    @FXML
    private TextField chatbar;

    @FXML
    private ImageView backicon;

    private ClientSocket clientSocket;
    private Chat chat;

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;

        if (this.clientSocket != null) {
            this.clientSocket.setOnMessageReceived(message -> Platform.runLater(() -> handleIncomingMessage(message)));
        }
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        if (chat != null && chat.getMessages() != null) {
            chat.getMessages().forEach(message -> {
                boolean isSentByCurrentUser = message.getSender().equals(chat.getParticipant1());
                addMessageToChat(message.getContent(), isSentByCurrentUser);
            });
        }
    }

    @FXML
    private void handleBackToChat() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty() && clientSocket != null) {
            clientSocket.sendMessage(message);
            chatbar.clear();
            addMessageToChat(message, true);
        }
    }

    private void handleIncomingMessage(String message) {
        if (chat == null) return;

        String[] parts = message.split(": ", 2);
        if (parts.length == 2) {
            String sender = parts[0].trim();
            String content = parts[1];
            if (sender.equalsIgnoreCase(chat.getParticipant1().getUsername().trim())) return;
            addMessageToChat(content, false);
        } else {
            addMessageToChat(message, false);
        }
    }

    private void addMessageToChat(String content, boolean isSentByCurrentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatBubble.fxml"));
            loader.setController(new ChatBubbleController());
            HBox bubble = loader.load();
    
            ChatBubbleController controller = loader.getController();
            controller.setMessage(content, isSentByCurrentUser);
    
            chatpreviewcontainer.getChildren().add(bubble);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
