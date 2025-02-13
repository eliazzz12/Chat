package com.dam.elias.chat.client.api.model;

import javafx.fxml.Initializable;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class Message implements Serializable, Comparable<Message> {
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
        sent = false;
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

    public String getTimeSent(){
        System.out.println("Message: obteniendo la hora");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    @Override
    public int compareTo(Message o) {
        return this.getTimestamp().compareTo(o.getTimestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(timestamp, message.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(timestamp);
    }
}
