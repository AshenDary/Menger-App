package com.example.controller;

import java.io.IOException;

import com.example.model.CurrentUser;
import com.example.model.User;
import com.example.network.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuController {

    @FXML
    private VBox rootlayoutchat;

    @FXML
    private HBox topbar;

    @FXML
    private Label chatsstaticlabel;

    @FXML
    private Label cfuserFullName;

    @FXML
    private Label cfuserUsername;

    @FXML
    private VBox bottomnavbar;

    @FXML
    private ImageView chaticon;

    @FXML
    private ImageView friendsicon;

    @FXML
    private ImageView menuicon;

    private User user;

    @FXML
    private void initialize() {
        User currentUser = CurrentUser.getInstance().getUser();

        if (currentUser != null) {
            if (currentUser.getFirstName() != null && currentUser.getLastName() != null) {
                cfuserFullName.setText(currentUser.getFullName());
            } else {
                cfuserFullName.setText(currentUser.getDisplayName());
            }
            cfuserUsername.setText(currentUser.getUsername());
        }
    }

    @FXML
    private void HandleChatTransfer() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void HandleFriendsTransfer() {
        try {
            MainClient.setRoot("friends", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
