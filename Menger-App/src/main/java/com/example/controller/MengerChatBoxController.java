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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MengerChatBoxController {

    @FXML
    private VBox addcontainer;

    @FXML
    private ImageView addgcicon;

    @FXML
    private ImageView addicon;

    @FXML
    private ImageView backicon;

    @FXML
    private ImageView callicon;

    @FXML
    private VBox cameracontainer;

    @FXML
    private ImageView cameraicon;

    @FXML
    private Label cfuserchatpreview;

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
    private Label chatsstaticlabel1;

    @FXML
    private StackPane iconswitcher;

    @FXML
    private VBox imagecontainer;

    @FXML
    private ImageView imageicon;

    @FXML
    private HBox jaridchatpreview1;

    @FXML
    private HBox jaridchatpreview2;

    @FXML
    private ImageView jaridicon1;

    @FXML
    private ImageView jaridicon2;

    @FXML
    private Label jaridmessagebubble1;

    @FXML
    private Label jaridmessagebubble2;

    @FXML
    private Label jaridmessagebubble3;

    @FXML
    private HBox jaridmessagebubble4;

    @FXML
    private HBox kenchatpreview1;

    @FXML
    private HBox kenchatpreview2;

    @FXML
    private ImageView kenicon1;

    @FXML
    private ImageView kenicon2;

    @FXML
    private Label kenmessagebubble1;

    @FXML
    private Label kenmessagebubble2;

    @FXML
    private ImageView kenpic2;

    @FXML
    private ImageView likeicon;

    @FXML
    private ImageView memberscontainer;

    @FXML
    private ImageView mengergroupicon;

    @FXML
    private ImageView mengergrouppfp;

    @FXML
    private VBox miccontainer;

    @FXML
    private ImageView micicon;

    @FXML
    private HBox middlenavbar;

    @FXML
    private ImageView namecontainer;

    @FXML
    private VBox pfpcontainer;

    @FXML
    private VBox rootlayoutchatboxmengergroup;

    @FXML
    private ImageView selectemojiicon;

    @FXML
    private ImageView sendicon;

    @FXML
    private Label staticat;

    @FXML
    private HBox topnavbar;

    @FXML
    private ImageView videocallicon;
    
    @FXML
    private VBox messageContainer;

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
        bubble.getStyleClass().add("message-bubble");
        messageContainer.getChildren().add(bubble);

        Platform.runLater(() -> chatareacontainer.setVvalue(1.0));
    }

}
