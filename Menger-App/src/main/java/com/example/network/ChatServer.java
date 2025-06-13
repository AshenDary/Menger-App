package com.example.network;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServer {

    private static final Set<Session> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Map<Session, String> usernames = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received: " + message);
        if (message.startsWith("USERNAME:")) {
            String username = message.substring("USERNAME:".length());
            usernames.put(session, username);
            broadcast("✅ " + username + " has joined the chat.");
        } else {
            String username = usernames.getOrDefault(session, "Unknown");
            broadcast("💬 " + username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        String username = usernames.remove(session);
        if (username != null) {
            broadcast("❌ " + username + " has left the chat.");
        }
        System.out.println("Connection closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on session " + session.getId() + ": " + throwable.getMessage());
    }

    private void broadcast(String message) {
        synchronized (sessions) {
            for (Session s : sessions) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
