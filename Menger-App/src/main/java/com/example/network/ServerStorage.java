package com.example.network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServerStorage {
    private static final String FILE_PATH = "chat_log.txt";
    

    public static synchronized void saveMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}