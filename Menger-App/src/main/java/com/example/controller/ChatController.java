package com.example.controller;

import com.example.InitializableWithData;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController implements InitializableWithData {

    @FXML
    private ImageView binoavataricon;

    @FXML
    private HBox binochatbox;

    @FXML
    private VBox binochatcontainer;

    @FXML
    private ImageView binochaticon;

    @FXML
    private Label binochatpreview;

    @FXML
    private VBox binostorycontainer;

    @FXML
    private Label binouser;

    @FXML
    private Label binousername;

    @FXML
    private HBox bottomnavbar;

    @FXML
    private VBox chatlist;

    @FXML
    private VBox chatscontainer;

    @FXML
    private ImageView chatsicon;

    @FXML
    private Label chatslabel;

    @FXML
    private Label chatsstaticlabel;

    @FXML
    private ImageView editicon;

    @FXML
    private VBox highlightscontainer;

    @FXML
    private ImageView highlightsicon;

    @FXML
    private Label highlightslabel;

    @FXML
    private ImageView jaridavataricon;

    @FXML
    private HBox jaridchatbox;

    @FXML
    private VBox jaridchatcontainer;

    @FXML
    private ImageView jaridchaticon;

    @FXML
    private Label jaridchatpreview;

    @FXML
    private VBox jaridstorycontainer;

    @FXML
    private Label jariduser;

    @FXML
    private Label jaridusername;

    @FXML
    private ImageView kenavataricon;

    @FXML
    private HBox kenchatbox;

    @FXML
    private VBox kenchatcontainer;

    @FXML
    private ImageView kenchaticon;

    @FXML
    private Label kenchatpreview;

    @FXML
    private VBox kenstorycontainer;

    @FXML
    private Label kenuser;

    @FXML
    private Label kenusername;

    @FXML
    private ImageView menuicon;

    @FXML
    private HBox mitaaichatbox;

    @FXML
    private VBox mitaaichatcontainer;

    @FXML
    private ImageView mitaaichaticon;

    @FXML
    private Label mitaaichatpreview;

    @FXML
    private VBox mitaaicontainer;

    @FXML
    private Label mitaailabel;

    @FXML
    private Label mitaaiuser;

    @FXML
    private ImageView mitaicon;

    @FXML
    private ImageView mitasearchicon;

    @FXML
    private ImageView raiavataricon;

    @FXML
    private HBox raichatbox;

    @FXML
    private VBox raichatcontainer;

    @FXML
    private ImageView raichaticon;

    @FXML
    private Label raichatpreview;

    @FXML
    private VBox raistorycontainer;

    @FXML
    private Label raiuser;

    @FXML
    private Label raiusername;

    @FXML
    private VBox rootlayoutchat;

    @FXML
    private TextField searchbar;

    @FXML
    private HBox searchfield;

    @FXML
    private ImageView siravataricon;

    @FXML
    private VBox sirstorycontainer;

    @FXML
    private HBox storiescontainer;

    @FXML
    private ScrollPane storiespane;

    @FXML
    private HBox topbar;

    @FXML
    private Label yournote;

    @FXML private ListView<String> messageList;
    @FXML private TextField inputField;

    private ChatClient chatClient;

    @Override
    public void init(Object data) {
        System.out.println("Initializing ChatController with data: " + data);
        String username = (String) data;
        try {
            chatClient = new ChatClient(username, msg -> messageList.getItems().add(msg));
        } catch (Exception e) {
            e.printStackTrace();
            messageList.getItems().add(" Failed to connect.");
        }
    }

    @FXML
    private void sendMessage() {
        String msg = inputField.getText();
        if (!msg.isBlank()) {
            chatClient.sendMessage(msg);
            inputField.clear();
        }
    }
}
