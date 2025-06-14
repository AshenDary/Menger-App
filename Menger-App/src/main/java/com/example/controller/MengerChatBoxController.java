package com.example.controller;

import java.io.IOException;

import com.example.model.CurrentUser;
import com.example.model.User;
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

    @FXML private VBox addContainer;
    @FXML private ImageView addGcIcon;
    @FXML private ImageView addIcon;
    @FXML private ImageView backIcon;
    @FXML private ImageView callIcon;
    @FXML private VBox cameraContainer;
    @FXML private ImageView cameraIcon;
    @FXML private ScrollPane chatAreaContainer;
    @FXML private TextField chatBar;
    @FXML private HBox chatBoxBottomNavBar;
    @FXML private HBox chatField;
    @FXML private VBox chatPreviewContainer;
    @FXML private Label chatStaticLabel;
    @FXML private Label chatStaticLabel1;
    @FXML private StackPane iconSwitcher;
    @FXML private VBox imageContainer;
    @FXML private ImageView imageIcon;
    @FXML private HBox jaridChatPreview1;
    @FXML private HBox jaridChatPreview2;
    @FXML private ImageView jaridIcon1;
    @FXML private ImageView jaridIcon2;
    @FXML private Label jaridMessageBubble1;
    @FXML private Label jaridMessageBubble2;
    @FXML private Label jaridMessageBubble3;
    @FXML private HBox jaridMessageBubble4;
    @FXML private HBox kenChatPreview1;
    @FXML private HBox kenChatPreview2;
    @FXML private ImageView kenIcon1;
    @FXML private ImageView kenIcon2;
    @FXML private Label kenMessageBubble1;
    @FXML private Label kenMessageBubble2;
    @FXML private ImageView kenPic2;
    @FXML private ImageView likeIcon;
    @FXML private ImageView membersIcon;
    @FXML private ImageView mengerGroupIcon;
    @FXML private ImageView mengerGroupPfp;
    @FXML private VBox micContainer;
    @FXML private ImageView micIcon;
    @FXML private HBox middleNavBar;
    @FXML private ImageView nameIcon;
    @FXML private VBox pfpContainer;
    @FXML private VBox rootLayoutChatBoxMengerGroup;
    @FXML private ImageView selectEmojiIcon;
    @FXML private ImageView sendIcon;
    @FXML private Label staticAt;
    @FXML private Label cfuserchatpreviewStatic;
    @FXML private HBox topNavBar;
    @FXML private ImageView videoCallIcon;
    @FXML private VBox messageContainer;

    private User user;

    @FXML
    private void handleBackToChat() {
        try {
            MainClient.setRoot("chat", CurrentUser.getInstance().getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSend() {
        String message = chatBar.getText().trim();
        if (!message.isEmpty()) {
            addMessageBubble(message);
            chatBar.clear();
        }
    }

    private void addMessageBubble(String message) {
        Text text = new Text(message);
        TextFlow bubble = new TextFlow(text);
        bubble.getStyleClass().add("message-bubble");
        messageContainer.getChildren().add(bubble);
        Platform.runLater(() -> chatAreaContainer.setVvalue(1.0));
    }

    public void init(Object data) {
        if (data instanceof User) {
            this.user = (User) data;
            System.out.println("Initializing ChatController with user: " + user.getFullName());
            
            if (user.getFirstName() != null && user.getLastName() != null) {
                cfuserchatpreviewStatic.setText(user.getFullName());
            } else {
                cfuserchatpreviewStatic.setText(user.getDisplayName());
            }
        }
    }


    @FXML
    private void initialize() {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null) {
            if (currentUser.getFirstName() != null && currentUser.getLastName() != null) {
                cfuserchatpreviewStatic.setText(currentUser.getFullName());
            } else {
                cfuserchatpreviewStatic.setText(currentUser.getDisplayName());
            }
        }
    }
}
