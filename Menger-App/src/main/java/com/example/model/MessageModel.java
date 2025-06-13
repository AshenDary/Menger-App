package com.example.model;

public class MessageModel {
    private String sender;
    private String message;

    public MessageModel(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public static MessageModel parseFromRaw(String rawMessage) {
        if (rawMessage.startsWith("USERNAME:")) {
            return new MessageModel("System", rawMessage.substring(9));
        }

        int colonIndex = rawMessage.indexOf(":");
        if (colonIndex != -1) {
            String sender = rawMessage.substring(0, colonIndex).trim();
            String message = rawMessage.substring(colonIndex + 1).trim();
            return new MessageModel(sender, message);
        }
        return new MessageModel("Unknown", rawMessage);
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
