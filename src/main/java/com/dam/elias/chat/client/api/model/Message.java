package com.dam.elias.chat.client.api.model;

import javafx.fxml.Initializable;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Message implements Serializable, Comparable<Message> {
    private String id = "";
    private int counter = 1;
    private User sender;
    private Chat chat;
    private String text;
    private LocalDateTime timestamp;
    private boolean sent;

    public Message(User sender, Chat chat, String text) {
        setSender(sender);
        setChat(chat);
        setText(text);
        timestamp = LocalDateTime.now();
        setId();
        sent = false;
    }

    private void setId() {
        //TODO que hacer con el id?
        id += sender.getUsername()+counter++;
    }

    public void addToChat(){
        chat.addMessage(this);
    }

    protected void setSender(User sender) {
        if(sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        this.sender = sender;
    }

    protected void setChat(Chat chat) {
        if(chat == null) {
            throw new IllegalArgumentException("Chat cannot be null");
        }
        this.chat = chat;
    }

    public void sent(){
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

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    @Override
    public int compareTo(Message o) {
        return this.getTimestamp().compareTo(o.getTimestamp());
    }
}
