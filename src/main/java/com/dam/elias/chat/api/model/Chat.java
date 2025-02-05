package com.dam.elias.chat.api.model;

import java.io.Serializable;
import java.util.List;

public abstract class Chat implements Serializable {
    private List<Message> messageList;
    private int unreadMessages;

    protected Message getLastMessage(){
        return messageList.getLast();
    }

    protected int getUnreadMessages(){
        return unreadMessages;
    }
}
