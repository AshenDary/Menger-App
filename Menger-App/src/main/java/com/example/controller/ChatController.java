package com.example.controller;

import java.io.IOException;

import com.example.InitializableWithData;
import com.example.MainClient;
import com.example.model.User;

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

    @FXML
    private Label cfuserchatpreview;

    private ChatClient chatClient;

    private User user;

    @Override
    public void init(Object data) {
        if (data instanceof User) {
            this.user = (User) data;
            System.out.println("Initializing ChatController with user: " + user.getFullName());
            
            if (user.getFirstName() != null && user.getLastName() != null) {
                cfuserchatpreview.setText(user.getFullName());
            } else {
                cfuserchatpreview.setText(user.getDisplayName());
            }
        }
    }


    @FXML
    private void initialize() {
        storiespane.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double scrollSpeed = 0.005;
            storiespane.setHvalue(storiespane.getHvalue() - deltaY * scrollSpeed);
            event.consume();
        });
        
    initializeBino();
    initializeJarid();
    initializeMitaAi();
    initializeKen();
    initializeRai();
    initializeBugoy();
}
    @FXML
    private void initializeBino() {
        binocontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("binochatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeJarid() {
        jaridchatcontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("jaridchatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeMitaAi() {
        mitaaichatcontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("mitaaichatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeKen() {
        kencontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("kenchatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeRai() {
        raicontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("raichatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeBugoy() {
        bugoycontainer.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("bugoychatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}