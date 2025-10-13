package com.example.controller;

import java.io.IOException;
import java.util.Stack;
import java.util.UUID;

import com.example.model.Chat;
import com.example.network.ClientSocket;
import com.example.network.MainClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BugoyChatBoxController {

    @FXML
    private ScrollPane chatareacontainer;

    @FXML
    private VBox chatpreviewcontainer;

    @FXML
    private TextField chatbar;

    @FXML
    private ImageView sendicon;

    @FXML
    private ImageView likeicon;

    @FXML
    private ImageView backicon;

    @FXML
    private Label messageLabel;

    private ClientSocket clientSocket;
    private Chat chat;

    private Stack<ChatBubbleController> sentMessages = new Stack<>();

    @FXML
    private void initialize() {
        Platform.runLater(this::scrollToBottom);

        chatbar.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean hasText = !newVal.trim().isEmpty();
            sendicon.setVisible(hasText);
            sendicon.setManaged(hasText);
            likeicon.setVisible(!hasText);
            likeicon.setManaged(!hasText);
        });

        chatbar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                handleSendMessage();
                event.consume();
            }
        });
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;

        if (this.clientSocket != null) {
            this.clientSocket.setOnMessageReceived(message -> Platform.runLater(() -> handleIncomingMessage(message)));
        }
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        if (chat != null && chat.getMessages() != null) {
            chat.getMessages().forEach(message -> {
                boolean isSentByCurrentUser = message.getSender().equals(chat.getParticipant1());
                // ✅ Generate message ID if not stored
                addMessageToChat(UUID.randomUUID().toString(), message.getContent(), isSentByCurrentUser);
            });
        }
    }

    @FXML
    private void handleBackToChat() {
        try {
            MainClient.setRoot("chat", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = chatbar.getText().trim();
        if (!message.isEmpty() && clientSocket != null) {
            String messageId = UUID.randomUUID().toString(); // ✅ Generate message ID
            clientSocket.sendMessage(message);
            chatbar.clear();
            addMessageToChat(messageId, message, true);
        }
    }

    private void handleIncomingMessage(String message) {
        if (chat == null) return;

        if (message.startsWith("MESSAGE:")) {
            String[] parts = message.split(":", 4);
            if (parts.length == 4) {
                String messageId = parts[1];
                String sender = parts[2];
                String content = parts[3];
                boolean isSentByCurrentUser = sender.equals(chat.getParticipant1().getUsername());
                addMessageToChat(messageId, content, isSentByCurrentUser);
            }
        } else if (message.startsWith("UNSEND:")) {
            String[] parts = message.split(":", 3);
            if (parts.length >= 2) {
                String messageId = parts[1];
                markMessageAsUnsent(messageId);
            }
        }
    }

    private void addMessageToChat(String messageId, String content, boolean isSentByCurrentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatBubble.fxml"));
            HBox bubble = loader.load();

            ChatBubbleController controller = loader.getController();
            controller.setMessage(content, isSentByCurrentUser);
            controller.setMessageId(messageId);

            bubble.setUserData(controller);

            if (isSentByCurrentUser) {
                controller.setOnUnsendCallback(() -> handleUnsend(controller));
                sentMessages.push(controller);
            }

            chatpreviewcontainer.getChildren().add(bubble);
            Platform.runLater(this::scrollToBottom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUnsend(ChatBubbleController controller) {
        controller.markAsUnsent();
        if (clientSocket != null) {
            clientSocket.sendUnsend(controller.getMessageId());
        }
    }

    private void markMessageAsUnsent(String messageId) {
        for (javafx.scene.Node node : chatpreviewcontainer.getChildren()) {
            if (node.getUserData() instanceof ChatBubbleController) {
                ChatBubbleController controller = (ChatBubbleController) node.getUserData();
                if (controller.getMessageId().equals(messageId)) {
                    controller.markAsUnsent();
                    break;
                }
            }
        }
    }

    private void scrollToBottom() {
        chatareacontainer.setVvalue(1.0);
    }
}
