package com.example.controller;

import java.io.IOException;

import com.example.InitializableWithData;
import com.example.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChatController implements InitializableWithData {

    @FXML
    private HBox binochatbox;

    @FXML
    private HBox binochatcontainer;

    @FXML
    private ImageView binochaticon;

    @FXML
    private Label binochatpreview;

    @FXML
    private VBox binocontainer;

    @FXML
    private VBox binostorycontainer;

    @FXML
    private ImageView binostoryicon;

    @FXML
    private Label binotime;

    @FXML
    private Label binouser;

    @FXML
    private Label binousername;

    @FXML
    private HBox bottomnavbar;

    @FXML
    private HBox bugoychatbox;

    @FXML
    private HBox bugoychatcontainer;

    @FXML
    private ImageView bugoychaticon;

    @FXML
    private Label bugoychatpreview;

    @FXML
    private VBox bugoycontainer;

    @FXML
    private VBox bugoystorycontainer;

    @FXML
    private ImageView bugoystoryicon;

    @FXML
    private Label bugoytime;

    @FXML
    private Label bugoyuser;

    @FXML
    private Label bugoyusername;

    @FXML
    private HBox cfchatbox;

    @FXML
    private HBox cfchatcontainer;

    @FXML
    private ImageView cfchaticon;

    @FXML
    private Label cfchatpreview;

    @FXML
    private VBox cfcontainer;

    @FXML
    private Label cftime;

    @FXML
    private Label cfuser;

    @FXML
    private VBox chatlist;

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
    private ImageView editicon;

    @FXML
    private ImageView facebookicon;

    @FXML
    private VBox friendscontainer;

    @FXML
    private ImageView friendsicon;

    @FXML
    private Label friendslabel;

    @FXML
    private HBox jaridchatbox;

    @FXML
    private HBox jaridchatcontainer;

    @FXML
    private ImageView jaridchaticon;

    @FXML
    private Label jaridchatpreview;

    @FXML
    private VBox jaridcontainer;

    @FXML
    private VBox jaridstorycontainer;

    @FXML
    private ImageView jaridstoryicon;

    @FXML
    private Label jaridtime;

    @FXML
    private Label jariduser;

    @FXML
    private Label jaridusername;

    @FXML
    private HBox kenchatbox;

    @FXML
    private HBox kenchatcontainer;

    @FXML
    private ImageView kenchaticon;

    @FXML
    private Label kenchatpreview;

    @FXML
    private VBox kencontainer;

    @FXML
    private VBox kenstorycontainer;

    @FXML
    private ImageView kenstoryicon;

    @FXML
    private Label kentime;

    @FXML
    private Label kenuser;

    @FXML
    private Label kenusername;

    @FXML
    private VBox menucontainer;

    @FXML
    private ImageView menuicon;

    @FXML
    private Label menulabel;

    @FXML
    private HBox mitaaichatbox;

    @FXML
    private HBox mitaaichatcontainer;

    @FXML
    private ImageView mitaaichaticon;

    @FXML
    private Label mitaaichatpreview;

    @FXML
    private VBox mitaaicontainer;

    @FXML
    private Label mitaaitime;

    @FXML
    private Label mitaaiuser;

    @FXML
    private ImageView mitasearchicon;

    @FXML
    private ImageView notificationicon;

    @FXML
    private HBox raichatbox;

    @FXML
    private HBox raichatcontainer;

    @FXML
    private ImageView raichaticon;

    @FXML
    private Label raichatpreview;

    @FXML
    private VBox raicontainer;

    @FXML
    private VBox raistorycontainer;

    @FXML
    private ImageView raistoryicon;

    @FXML
    private Label raitime;

    @FXML
    private Label raiuser;

    @FXML
    private Label raiusername;

    @FXML
    private VBox rootlayoutchat;

    @FXML
    private TextField searchfield;
    @FXML
    private HBox searchfieldBox;

    @FXML
    private VBox sirstorycontainer;

    @FXML
    private ImageView sirstoryicon;

    @FXML
    private HBox storiescontainer;

    @FXML
    private ScrollPane storiespane;

    @FXML
    private HBox topbar;

    @FXML
    private Label yournote;

    private ChatClient chatClient;

    @Override
    public void init(Object data) {
        System.out.println("Initializing ChatController with data: " + data);
        // String username = (String) data;
        // try {
        //     chatClient = new ChatClient(username, msg -> messageList.getItems().add(msg));
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     if (messageList != null) messageList.getItems().add(" Failed to connect.");
        // }
    }

    @FXML
    private void initialize() {
        // Add click event to binocontainer
        binocontainer.setOnMouseClicked(event -> {
            try {
                // Switch to binochatbox.fxml scene
                MainClient.setRoot("binochatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}