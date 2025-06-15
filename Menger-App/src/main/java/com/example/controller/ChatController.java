package com.example.controller;

import java.io.IOException;

import com.example.model.Chat;
import com.example.model.CurrentUser;
import com.example.model.InitializableWithData;
import com.example.model.User;
import com.example.network.MainClient;

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
    private TextField chatInput;

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

    @FXML
    private VBox chatVBox;

    private User user;

    private static VBox staticChatVBox;

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
        User currentUser = CurrentUser.getInstance().getUser();
        staticChatVBox = chatVBox;
        if (currentUser != null) {
            if (currentUser.getFirstName() != null && currentUser.getLastName() != null) {
                cfuserchatpreview.setText(currentUser.getFullName());
            } else {
                cfuserchatpreview.setText(currentUser.getDisplayName());
            }
        }

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
        initializeMenger();
        initializeMenu();
    }
    @FXML
    private void initializeBino() {
        binochatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("binochatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeJarid() {
        jaridchatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("jaridchatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeMitaAi() {
        mitaaichatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("mitaaichatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeKen() {
        kenchatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("kenchatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeRai() {
        raichatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("raichatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void initializeBugoy() {
        bugoychatbox.setOnMouseClicked(event -> {
            try {
                User sender = CurrentUser.getInstance().getUser();
                User bugoy = new User("bugoy");
                bugoy.setDisplayName("Bugoy");

                Chat chat = new Chat(sender, bugoy);
                MainClient.setRoot("bugoychatbox", chat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void initializeMenger() {
        cfchatbox.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("mengerchatbox", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeMenu() {
        menuicon.setOnMouseClicked(event -> {
            try {
                MainClient.setRoot("menu", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}