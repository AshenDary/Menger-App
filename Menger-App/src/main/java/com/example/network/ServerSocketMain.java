package com.example.network;

import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ServerSocketMain {
    private static final ConcurrentHashMap<String, Session> websocketClients = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket client connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("📩 Received WebSocket message: " + message);

        if (message.startsWith("USERNAME:")) {
            String username = message.substring(9);
            websocketClients.put(username, session);
            broadcast(username + " has joined the chat.");
        } else {
            String username = websocketClients.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(session))
                    .map(entry -> entry.getKey())
                    .findFirst()
                    .orElse("Unknown");
            String fullMessage = username + ": " + message;
            ServerStorage.saveMessage(fullMessage);
            broadcastToOthers(fullMessage, session);
        }
    }

    public static void broadcast(String message) {
        websocketClients.values().forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void broadcastToOthers(String message, Session senderSession) {
        websocketClients.values().forEach(session -> {
            if (!session.equals(senderSession)) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}