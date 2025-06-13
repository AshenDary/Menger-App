package com.example.network;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServerEndpoint {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Session, String> userMap = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) {
        System.out.println("Received: " + message);

        if (message.startsWith("USERNAME:")) {
            String username = message.substring(9);
            userMap.put(senderSession, username);
            return;
        }

        String senderUsername = userMap.get(senderSession);

        // Broadcast to all sessions EXCEPT the sender
        synchronized (sessions) {
            for (Session session : sessions) {
                if (!session.equals(senderSession) && session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(senderUsername + ": " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        userMap.remove(session);
        System.out.println("Session closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on session " + session.getId() + ": " + throwable.getMessage());
    }
}
