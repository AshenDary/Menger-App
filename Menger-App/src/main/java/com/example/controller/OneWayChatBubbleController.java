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
    }
}
