package com.dam.elias.chat.api.model;

import java.io.Serializable;
import java.util.List;

public abstract class Chat implements Serializable {
    protected String name;
    private List<Message> messageList;
    private int unreadMessages;

    public Chat(String name) {
        setName(name);
    }

    protected Message getLastMessage(){
        return messageList.getLast();
    }

    public int getUnreadMessages(){
        return unreadMessages;
    }

    private void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
}
