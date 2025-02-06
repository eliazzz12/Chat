package com.dam.elias.chat.client.api.model.exceptions;

public class UsernameBeingUsedException extends RuntimeException {
    public UsernameBeingUsedException(String message) {
        super(message);
    }
    public UsernameBeingUsedException() {}
}
