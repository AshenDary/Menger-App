package com.example.controller;

import java.io.IOException;

import com.example.network.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class FriendsController {

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private boolean isPlaying1 = false;
    private boolean isPlaying2 = false;

    @FXML
    private Button playbutton;

    @FXML
    private Button playbutton1;

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

    @FXML
    private void handlePlayAudio2() {
        try {
            // Stop mediaPlayer1 if running
            if (mediaPlayer1 != null) {
                mediaPlayer1.stop();
                isPlaying1 = false;
    
                ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                icon.setFitWidth(12);
                icon.setFitHeight(12);
                playbutton.setGraphic(icon);
            }
    
            if (mediaPlayer2 == null) {
                String audioPath = getClass().getResource("/assets/audio/bugoywaveform.mp3").toExternalForm();
                Media media = new Media(audioPath);
                mediaPlayer2 = new MediaPlayer(media);
    
                mediaPlayer2.setOnEndOfMedia(() -> {
                    isPlaying2 = false;
                    ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                    icon.setFitWidth(12);
                    icon.setFitHeight(12);
                    playbutton1.setGraphic(icon);
                });
            }
    
            if (isPlaying2) {
                mediaPlayer2.pause();
                ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                icon.setFitWidth(12);
                icon.setFitHeight(12);
                playbutton1.setGraphic(icon);
            } else {
                mediaPlayer2.play();
                ImageView pauseIcon = new ImageView(new Image(getClass().getResource("/assets/images/pauseicon.png").toExternalForm()));
                pauseIcon.setFitWidth(12);
                pauseIcon.setFitHeight(12);
                playbutton1.setGraphic(pauseIcon);
            }
    
            isPlaying2 = !isPlaying2;
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handlePlayAudio1() {
        try {
            // Stop mediaPlayer2 if running
            if (mediaPlayer2 != null) {
                mediaPlayer2.stop();
                isPlaying2 = false;
    
                ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                icon.setFitWidth(12);
                icon.setFitHeight(12);
                playbutton1.setGraphic(icon);
            }
    
            if (mediaPlayer1 == null) {
                String audioPath = getClass().getResource("/assets/audio/binowaveform.mp3").toExternalForm();
                Media media = new Media(audioPath);
                mediaPlayer1 = new MediaPlayer(media);
    
                mediaPlayer1.setOnEndOfMedia(() -> {
                    isPlaying1 = false;
                    ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                    icon.setFitWidth(12);
                    icon.setFitHeight(12);
                    playbutton.setGraphic(icon);
                });
            }
    
            if (isPlaying1) {
                mediaPlayer1.pause();
                ImageView icon = new ImageView(new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm()));
                icon.setFitWidth(12);
                icon.setFitHeight(12);
                playbutton.setGraphic(icon);
            } else {
                mediaPlayer1.play();
                ImageView pauseIcon = new ImageView(new Image(getClass().getResource("/assets/images/pauseicon.png").toExternalForm()));
                pauseIcon.setFitWidth(12);
                pauseIcon.setFitHeight(12);
                playbutton.setGraphic(pauseIcon);
            }
    
            isPlaying1 = !isPlaying1;
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    @FXML
    private void initialize() {
        // Optional: Scroll behavior for stories pane
        if (storiespane != null) {
            storiespane.setOnScroll(event -> {
                double deltaY = event.getDeltaY();
                double scrollSpeed = 0.005;
                double newValue = storiespane.getHvalue() - deltaY * scrollSpeed;
                newValue = Math.max(0, Math.min(1, newValue)); // clamp between 0 and 1
                storiespane.setHvalue(newValue);
                event.consume();
            });
        }
    
        // Resize play icon for playbutton
        if (playbutton != null) {
            Image playImage = new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm());
            ImageView playView = new ImageView(playImage);
            playView.setFitWidth(12);  // Adjust width
            playView.setFitHeight(12); // Adjust height
            playbutton.setGraphic(playView);
        }
    
        // Resize play icon for playbutton1
        if (playbutton1 != null) {
            Image playImage1 = new Image(getClass().getResource("/assets/images/playicon.png").toExternalForm());
            ImageView playView1 = new ImageView(playImage1);
            playView1.setFitWidth(12);  // Adjust width
            playView1.setFitHeight(12); // Adjust height
            playbutton1.setGraphic(playView1);
        }
    }    
}
    