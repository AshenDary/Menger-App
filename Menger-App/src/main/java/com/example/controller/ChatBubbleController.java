package com.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ChatBubbleController {

    @FXML
    private HBox bubbleContainer;

    @FXML
    private HBox messageContainer;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label senderName;

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView unsendIcon;

    private boolean isOwnMessage;
    private Runnable onUnsendCallback;

    private String messageId;

    public void setMessageId(String id) {
        this.messageId = id;
    }

    public String getMessageId() {
        return messageId;
    }

    

    @FXML
    private void initialize() {
        unsendIcon.setVisible(false);
        unsendIcon.setManaged(false);

        bubbleContainer.setOnMouseEntered(e -> {
            if (isOwnMessage) {
                unsendIcon.setVisible(true);
                unsendIcon.setManaged(true);
            }
        });

        bubbleContainer.setOnMouseExited(e -> {
            if (isOwnMessage) {
                unsendIcon.setVisible(false);
                unsendIcon.setManaged(false);
            }
        });
    }


    public void setMessage(String message, boolean isOwnMessage) {
        this.isOwnMessage = isOwnMessage;
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-font-size: 15px; -fx-background-radius: 20; -fx-padding: 8;"
                + (isOwnMessage
                ? "-fx-background-color: #0084FF; -fx-text-fill: white;"
                : "-fx-background-color: #e4e6eb; -fx-text-fill: black;"));

        if (isOwnMessage) {
            profilePic.setVisible(false);
            senderName.setVisible(false);
            unsendIcon.setImage(new Image(getClass().getResource("/assets/images/undo.png").toExternalForm()));
            messageContainer.setAlignment(Pos.CENTER_RIGHT);
            bubbleContainer.setStyle("-fx-alignment: CENTER_RIGHT;");
        } else {
            profilePic.setVisible(true);
            profilePic.setImage(new Image(getClass().getResource("/assets/images/bugoyicon.png").toExternalForm()));
            senderName.setVisible(false);
            unsendIcon.setVisible(false);
            messageContainer.setAlignment(Pos.CENTER_LEFT);
            bubbleContainer.setStyle("-fx-alignment: CENTER_LEFT;");
        }
    }

    public void handleUnsendClicked() {
        if (onUnsendCallback != null) {
            onUnsendCallback.run();
        }
    }

    public void setOnUnsendCallback(Runnable callback) {
        this.onUnsendCallback = callback;
    }

    public void markAsUnsent() {
        messageLabel.setText("unsint a message.");
        messageLabel.setStyle("-fx-font-size: 15px; -fx-background-color: #e4e6eb; -fx-text-fill: gray; -fx-background-radius: 20; -fx-padding: 8;");
        unsendIcon.setVisible(false);
    }
}
