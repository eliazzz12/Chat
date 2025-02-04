package com.dam.elias.chat.api.model;

import java.time.LocalDateTime;

public class Message {
    private String idSender, idReceiver;
    private String text;
    private LocalDateTime timestamp;

    protected Message(String idSender, String idReceiver, String text, LocalDateTime timestamp) {
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setText(text);
    }

    protected void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    protected void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    protected void setText(String text) {
        this.text = text;
    }

    public String getIdSender() {
        return idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public String getText() {
        return text;
    }
}
