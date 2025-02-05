package com.dam.elias.chat.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String id;
    private int counter = 1;
    private User sender;
    private User receiver;
    private Chat chat;
    private String text;
    private LocalDateTime timestamp;
    private boolean sent;

    protected Message(User sender, User receiver, Chat chat, String text) {
        setSender(sender);
        setReceiver(receiver);
        setChat(chat);
        setText(text);
        timestamp = LocalDateTime.now();
        setId();
        sent = false;
    }

    private void setId() {
        //TODO que hacer con el id?
        id = sender.getUsername()+counter++;
    }

    protected void setSender(User sender) {
        if(sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        this.sender = sender;
    }

    protected void setReceiver(User receiver) {
        if(receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null");
        }
        this.receiver = receiver;
    }

    protected void setChat(Chat chat) {
        if(chat == null) {
            throw new IllegalArgumentException("Chat cannot be null");
        }
        this.chat = chat;
    }

    protected void sent(){
        sent = true;
    }

    protected void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public Chat getChat() {
        return chat;
    }

    public boolean isSent() {
        return sent;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
