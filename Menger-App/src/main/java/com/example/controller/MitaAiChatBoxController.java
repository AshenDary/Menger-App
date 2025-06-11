package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MitaAiChatBoxController {

    @FXML
    private VBox addcontainer;

    @FXML
    private ImageView addicon;

    @FXML
    private VBox cameracontainer;

    @FXML
    private ImageView cameraicon;

    @FXML
    private ScrollPane chatareacontainer;

    @FXML
    private TextField chatbar;

    @FXML
    private HBox chatboxbottomnavbar;

    @FXML
    private HBox chatfield;

    @FXML
    private VBox chatpreviewcontainer;

    @FXML
    private Label chatsstaticlabel;

    @FXML
    private Label chatstaticlabel1;

    @FXML
    private StackPane iconswitcher;

    @FXML
    private VBox imagecontainer;

    @FXML
    private ImageView imageicon;

    @FXML
    private ImageView informationicon;

    @FXML
    private ImageView likeicon;

    @FXML
    private VBox miccontainer;

    @FXML
    private ImageView micicon;

    @FXML
    private HBox mitaaichatpreview1;

    @FXML
    private ImageView mitaaiicon;

    @FXML
    private ImageView mitaaiicon1;

    @FXML
    private Label mitaaimessagebubble1;

    @FXML
    private ImageView mitaaipfp1;

    @FXML
    private VBox pfpcontainer;

    @FXML
    private VBox rootlayoutchatboxken;

    @FXML
    private ImageView selectemojiicon;

    @FXML
    private ImageView sendicon;

    @FXML
    private HBox sirchatpreview1;

    @FXML
    private ImageView siricon1;

    @FXML
    private Label sirmessagebubble1;

    @FXML
    private Label timestamp1;

    @FXML
    private HBox topnavbar;

    @FXML
    private ImageView verificationicon;

    @FXML
    private ImageView backicon;

    /**
     * Handles the back button click to navigate back to the main chat screen.
     */
    @FXML
    private void handleBackToChat() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the send button click to send a message.
     */
    @FXML
    private void handleSend() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty()) {
            // Add the message to the chat preview container
            Platform.runLater(() -> {
                Label userMessage = new Label(message);
                userMessage.setStyle("-fx-background-color: #2b63f4; -fx-background-radius: 30; -fx-text-fill: white; -fx-padding: 10;");
                userMessage.setWrapText(true);
                HBox userMessageContainer = new HBox(userMessage);
                userMessageContainer.setStyle("-fx-alignment: center-right; -fx-padding: 5;");
                chatpreviewcontainer.getChildren().add(userMessageContainer);
            });

            // Clear the chat bar
            chatbar.clear();
        }
    }
}