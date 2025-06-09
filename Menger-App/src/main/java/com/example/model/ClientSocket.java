package com.example.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final String username;

    public ClientSocket(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public String getUsername() {
        return username;
    }

    public void close() throws IOException {
        socket.close();
    }
}