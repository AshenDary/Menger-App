package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuController {

    @FXML
    private HBox bottomnavbar;

    @FXML
    private ImageView chaticon;

    @FXML
    private VBox chatscontainer;

    @FXML
    private Label chatslabel;

    @FXML
    private StackPane chatsstackpane;

    @FXML
    private Label chatsstaticlabel;

    @FXML
    private VBox friendscontainer;

    @FXML
    private ImageView friendsicon;

    @FXML
    private Label friendslabel;

    @FXML
    private VBox menucontainer;

    @FXML
    private ImageView menuicon;

    @FXML
    private Label menulabel;

    @FXML
    private VBox rootlayoutchat;

    @FXML
    private HBox topbar;

    @FXML
        private void HandleChatTransfer() {
            try {
                MainClient.setRoot("chat", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



}
