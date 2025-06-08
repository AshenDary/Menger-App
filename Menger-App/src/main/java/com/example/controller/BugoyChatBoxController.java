package com.example.controller;

import java.io.IOException;

import com.example.MainClient;
import com.example.model.Chat;
import com.example.model.Message;
import com.example.network.ClientSocket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BugoyChatBoxController {

    @FXML
    private VBox chatVBox;

    @FXML
    private TextField chatbar;

    @FXML
    private ImageView backicon;

    private ClientSocket clientSocket;
    private Chat chat;

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setChat(Chat chat) {
        this.chat = chat;

        // Load existing messages into the chat view
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
    private void initialize() {
        new Thread(() -> {
            try {
                String message;
                while ((message = clientSocket.receiveMessage()) != null) {
                    String finalMessage = message;
                    Platform.runLater(() -> addMessageToChat(finalMessage, false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleSendMessage() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty()) {
            clientSocket.sendMessage(message);
            addMessageToChat("You: " + message, true);

            if (chat != null) {
                // Add the message to the chat with the current timestamp
                chat.addMessage(new Message(chat.getParticipant1(), message, java.time.LocalDateTime.now()));
            }

            chatbar.clear();
        }
    }

    private void addMessageToChat(String content, boolean isSentByCurrentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/ChatBubble.fxml"));
            HBox bubble = loader.load();

            ChatBubbleController controller = loader.getController();
            controller.setMessage(content, isSentByCurrentUser);

            chatVBox.getChildren().add(bubble);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}