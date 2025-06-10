package com.example.controller;

import java.io.IOException;

import com.example.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class KenChatBoxController {

    @FXML
    private ScrollPane chatareacontainer;

    @FXML
    private VBox messageContainer; // VBox inside ScrollPane

    @FXML
    private TextField chatbar;

    @FXML
    private ImageView sendicon;

    @FXML
    private ImageView likeicon;

    @FXML
    private ImageView backicon;

    @FXML
    private void initialize() {
        // Auto-scroll to bottom on load
        Platform.runLater(() -> chatareacontainer.setVvalue(1.0));
    
        // Toggle send and like icons based on input
        chatbar.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean hasText = !newVal.trim().isEmpty();
            sendicon.setVisible(hasText);
            sendicon.setManaged(hasText);
            likeicon.setVisible(!hasText);
            likeicon.setManaged(!hasText);
        });
    
        // Enter key to send message
        chatbar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                handleSend();
                event.consume();
            }
        });
    
        // Typing toggle (optional)
        chatbar.textProperty().addListener((obs, oldVal, newVal) -> {
            // You can toggle send icon here if needed
            boolean hasText = !newVal.trim().isEmpty();
            sendicon.setVisible(hasText);
            sendicon.setManaged(hasText);
        });
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
    private void handleSend() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty()) {
            addMessageBubble(message);
            chatbar.clear();
        }
    }

    private void addMessageBubble(String message) {
        Text text = new Text(message);
        TextFlow bubble = new TextFlow(text);
        bubble.getStyleClass().add("message-bubble"); // Define in CSS if needed
        messageContainer.getChildren().add(bubble);

        // Auto-scroll to bottom
        Platform.runLater(() -> chatareacontainer.setVvalue(1.0));
    }
}