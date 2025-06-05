package com.example.model;

import java.time.LocalDateTime;

public class Message {
    private User sender;
    private String content;
    private LocalDateTime timestamp;
    private boolean isSentByCurrentUser;

    public Message(User sender, String content, boolean isSentByCurrentUser) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now(); // current time
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    // Getters and Setters
}
