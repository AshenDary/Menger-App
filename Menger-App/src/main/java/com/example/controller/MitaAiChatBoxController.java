package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MitaAiChatBoxController {

    @FXML
    private ScrollPane chatareacontainer;

    @FXML
    private VBox chatpreviewcontainer;

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
        Platform.runLater(this::scrollToBottom);

        chatbar.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean hasText = !newVal.trim().isEmpty();
            sendicon.setVisible(hasText);
            sendicon.setManaged(hasText);
            likeicon.setVisible(!hasText);
            likeicon.setManaged(!hasText);
        });

        chatbar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                handleSendMessage();
                event.consume();
            }
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
    private void handleSendMessage() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty()) {
            chatbar.clear();
            addMessageToChat(message);
        }
    }

    private void addMessageToChat(String content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OneWayChatBubble.fxml"));
            HBox bubble = loader.load();

            OneWayChatBubbleController controller = loader.getController();
            controller.setMessage(content);

            chatpreviewcontainer.getChildren().add(bubble);
            scrollToBottom();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scrollToBottom() {
        chatareacontainer.setVvalue(1.0);
    }
}
