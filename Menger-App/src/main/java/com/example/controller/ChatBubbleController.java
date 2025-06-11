package com.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ChatBubbleController {

    @FXML
    private HBox root;

    @FXML
    private Label messageLabel;

    public void setMessage(String message, boolean isSentByCurrentUser) {
        messageLabel.setText(message);

        if (isSentByCurrentUser) {
            root.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #0084FF; -fx-text-fill: white; -fx-padding: 10 15 10 15; -fx-background-radius: 12;");
        } else {
            root.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #E4E6EB; -fx-text-fill: black; -fx-padding: 10 15 10 15; -fx-background-radius: 12;");
        }
    }

    public Node getRoot() {
        return root;
    }
}