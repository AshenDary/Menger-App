package com.example.network;

import java.net.URI;
import java.util.function.Consumer;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@ClientEndpoint
public class ClientSocket {

    private Session session;
    private final String username;
    private Consumer<String> onMessageReceived;

    public ClientSocket(String username, Consumer<String> onMessageReceived) throws Exception {
        this.username = username;
        this.onMessageReceived = onMessageReceived;

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, new URI("ws://localhost:8080/chat"));
        sendMessage("USERNAME:" + username);
    }

    @OnMessage
    public void onMessage(String message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message);
        }
    }

    public void setOnMessageReceived(Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    public void sendMessage(String message) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(message);
            } else {
                System.err.println("WebSocket session is closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
