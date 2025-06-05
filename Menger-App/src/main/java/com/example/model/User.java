package com.example.model;

public class User {
    private String username;
    private String displayName;
    private String status; // e.g., "online", "offline", "typing..."
    private String profilePicturePath; // optional

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
        this.status = "offline";
    }

    // Getters and Setters
}
