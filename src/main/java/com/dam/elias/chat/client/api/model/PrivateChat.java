package com.dam.elias.chat.client.api.model;

public class PrivateChat extends Chat {
    private User clientUser, otherUser;

    public PrivateChat(User clientUser, User otherUser) {
        super(otherUser.getUsername());
    }

    public User getOtherUser() {
        return otherUser;
    }



}
