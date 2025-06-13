package com.example.network;

import java.net.URI;
import java.util.function.Consumer;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
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
        connect();
    }

    private void connect() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, new URI("ws://localhost:8080/chat"));
        sendMessage("USERNAME:" + username);
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("✅ Connected to server: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("❌ Disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("⚠️ Error: " + throwable.getMessage());
    }

    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("❗ WebSocket session is closed. Attempting to reconnect...");
            try {
                reconnect();
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                System.err.println("❌ Failed to reconnect WebSocket.");
                e.printStackTrace();
            }
        }
    }

    private void reconnect() throws Exception {
        connect();
    }

    public void setOnMessageReceived(Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    public void close() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
