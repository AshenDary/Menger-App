package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.application.Platform;

public class ChatClient {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onMessageReceived;

    public ChatClient(String username, Consumer<String> onMessageReceived) throws IOException {
        this.onMessageReceived = onMessageReceived;
        socket = new Socket("localhost", 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(() -> {
            try {
                out.println(username);
                String line;
                while ((line = in.readLine()) != null) {
                    String msg = line;
                    Platform.runLater(() -> this.onMessageReceived.accept(msg));
                }
            } catch (IOException e) {
                Platform.runLater(() -> this.onMessageReceived.accept(" Disconnected from server"));
            }
        }).start();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() throws IOException {
        socket.close();
    }
}
