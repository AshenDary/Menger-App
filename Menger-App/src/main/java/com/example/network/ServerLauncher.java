package com.example.network;

import org.glassfish.tyrus.server.Server;

public class ServerLauncher {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/", null, ChatServer.class);

        try {
            System.out.println("Starting WebSocket server...");
            server.start();
            System.out.println("WebSocket server started at ws://localhost:8080/chat");
            System.out.println("Press Ctrl+C to stop the server.");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
