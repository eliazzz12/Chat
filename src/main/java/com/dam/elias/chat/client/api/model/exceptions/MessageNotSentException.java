package com.dam.elias.chat.client.api.model.exceptions;

public class MessageNotSentException extends RuntimeException {
    public MessageNotSentException(String message) {
        super(message);
    }
    public MessageNotSentException(Exception e) {
        super(e);
    }
}
