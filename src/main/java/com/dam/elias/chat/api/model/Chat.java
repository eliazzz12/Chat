package com.dam.elias.chat.api.model;

import java.util.List;

public abstract class Chat {
    private int id;
    private List<Message> messageList;
    private int unreadMessages;

    protected Message getLastMessage(){
        return messageList.getLast();
    }
}
