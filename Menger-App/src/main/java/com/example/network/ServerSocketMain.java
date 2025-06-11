package com.example.network;

import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
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
            broadcast(fullMessage);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String username = websocketClients.entrySet().stream()
                .filter(entry -> entry.getValue().equals(session))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse("Unknown");
        websocketClients.remove(username);
        broadcast(username + " has left the chat.");
        System.out.println("WebSocket client disconnected: " + session.getId());
    }

    public static void broadcast(String message) {
        System.out.println("Broadcasting message: " + message);

        websocketClients.values().forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        clients.values().forEach(client -> client.sendMessage(message));
    }
}