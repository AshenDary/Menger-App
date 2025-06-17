package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FriendsController {

    @FXML
    private HBox binomessagebubble;

    @FXML
    private HBox binonotes;

    @FXML
    private ImageView binostory;

    @FXML
    private VBox binostorypanel;

    @FXML
    private HBox bottomnavbar;

    @FXML
    private HBox bugoymessagebubble;

    @FXML
    private HBox bugoynotes;

    @FXML
    private ImageView bugoystory;

    @FXML
    private VBox bugoystorypanel;

    @FXML
    private VBox chatscontainer;

    @FXML
    private ImageView chatsicon;

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
    private HBox friendsstoriescontainer;

    @FXML
    private HBox jaridnotes;

    @FXML
    private ImageView jaridstory;

    @FXML
    private VBox jaridstorypanel;

    @FXML
    private ImageView kenstory;

    @FXML
    private VBox kenstorypanel;

    @FXML
    private HBox kinnethnotes;

    @FXML
    private VBox menucontainer;

    @FXML
    private ImageView menuicon;

    @FXML
    private Label menulabel;

    @FXML
    private Button playbutton;

    @FXML
    private Button playbutton1;

    @FXML
    private HBox rainotes;

    @FXML
    private ImageView raistory;

    @FXML
    private VBox raistorypanel;

    @FXML
    private VBox rootlayoutchat;

    @FXML
    private HBox sendmessage;

    @FXML
    private HBox sendmessage1;

    @FXML
    private HBox sendmessage2;

    @FXML
    private HBox sendmessage3;

    @FXML
    private HBox sendmessage4;

    @FXML
    private ImageView sirstory;

    @FXML
    private VBox sirstorypanel;

    @FXML
    private ScrollPane storiespane;

    @FXML
    private HBox topbar;

    @FXML
    private ImageView waveform;

    @FXML
    private ImageView waveform1;

    @FXML
    private void HandleChatTransfer() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void HandleMenuTransfer() {
        try {
            MainClient.setRoot("menu", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
