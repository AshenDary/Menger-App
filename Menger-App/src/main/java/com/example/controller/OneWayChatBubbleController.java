package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class OneWayChatBubbleController {

    @FXML
    private HBox bubbleContainer;

    @FXML
    private Label messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-background-color: #0084FF; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 12; -fx-padding: 8;");
        bubbleContainer.setStyle("-fx-alignment: CENTER_RIGHT;");
    }
}
