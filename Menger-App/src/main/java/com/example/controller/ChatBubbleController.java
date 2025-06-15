package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ChatBubbleController {

    @FXML
    private HBox bubbleContainer;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label senderName;

    @FXML
    private Label messageLabel;

    public void setMessage(String message, boolean isOwnMessage) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-font-size: 15px; -fx-background-radius: 12; -fx-padding: 8;" +
                              (isOwnMessage 
                               ? "-fx-background-color: #0084FF; -fx-text-fill: white;"
                               : "-fx-background-color: #e4e6eb; -fx-text-fill: black;"));
    
        if (isOwnMessage) {
            profilePic.setVisible(false);
            senderName.setVisible(false);
            bubbleContainer.setStyle("-fx-alignment: CENTER_RIGHT;");
        } else {
            profilePic.setVisible(true);
            profilePic.setImage(new Image(getClass().getResource("/assets/images/bugoyicon.png").toExternalForm()));
            senderName.setVisible(false);
            bubbleContainer.setStyle("-fx-alignment: CENTER_LEFT;");
        }
    }
    
}
