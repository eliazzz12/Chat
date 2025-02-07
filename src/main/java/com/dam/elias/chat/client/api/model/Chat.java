package com.dam.elias.chat.client.api.model;

import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class Chat implements Serializable, Comparable<Chat> {
    protected String name;
    private List<Message> messageList;
    private int unreadMessages;
    private ChatViewController viewController;
    private ChatInfoController infoController;

    public Chat(String name) {
        setName(name);
    }

    public boolean isPrivate(){
        return this.getClass() == PrivateChat.class;
    }

    public Message getLastMessage(){
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

    public String getName() {
        return name;
    }

    public ChatViewController getViewController() {
        return viewController;
    }

    public ChatInfoController getInfoController() {
        return infoController;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    @Override
    public int compareTo(Chat o) {
        return this.getLastMessage().getTimestamp().compareTo(o.getLastMessage().getTimestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(name, chat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
