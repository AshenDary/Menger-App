package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Enter your username:");
            username = in.readLine();
            System.out.println("Client connected with username: " + username);

            if (username == null || username.isBlank()) {
                out.println("Invalid username. Disconnecting.");
                clientSocket.close();
                return;
            }

            ServerSocketMain.clients.put(username, this);
            ServerSocketMain.broadcast(username + " has joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {
                String fullMessage = username + ": " + message;
                System.out.println("📩 Received message: " + fullMessage);
                ServerStorage.saveMessage(fullMessage);
                ServerSocketMain.broadcast(fullMessage);
            }
        } catch (IOException e) {
            System.out.println("Disconnected: " + username);
        } finally {
            try {
                if (username != null) {
                    ServerSocketMain.clients.remove(username);
                    ServerSocketMain.broadcast(username + " has left the chat.");
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}