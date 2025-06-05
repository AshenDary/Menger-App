package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private User participant1;
    private User participant2;
    private List<Message> messages;

    public Chat(User participant1, User participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    // Getters and chat management methods
}
