package com.example.model;

import java.time.LocalDateTime;

public class Message {
    private User sender;
    private String content;
    private LocalDateTime timestamp;

    public Message(User sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String toFileString() {
        return sender.getUsername() + "|" + timestamp.toString() + "|" + content;
    }

    public static Message fromFileString(String line) {
        String[] parts = line.split("\\|", 3);
        return new Message(new User(parts[0]), parts[2], LocalDateTime.parse(parts[1]));
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}