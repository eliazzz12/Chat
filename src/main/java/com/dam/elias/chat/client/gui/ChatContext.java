package com.dam.elias.chat.client.gui;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.gui.states.State;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;
import javafx.scene.Parent;

public class ChatContext {
    private Chat chat;
    private Parent infoItem;
    private State state;
    private ChatInfoController chatInfoController;
    private ChatViewController chatViewController;

    public ChatContext(Chat chat, Parent infoItem,
                       ChatInfoController chatInfoController, ChatViewController chatViewController) {
        setChat(chat);
        setInfoItem(infoItem);
        setChatInfoController(chatInfoController);
        setChatViewController(chatViewController);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setInfoItem(Parent item) {
        if(item == null) {
            throw new IllegalArgumentException("Info Item cannot be null");
        }
        infoItem = item;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setChatInfoController(ChatInfoController chatInfoController) {
        this.chatInfoController = chatInfoController;
    }

    public void setChatViewController(ChatViewController chatViewController) {
        this.chatViewController = chatViewController;
    }

    public Chat getChat() {
        return chat;
    }

    public Parent getInfoItem() {
        return infoItem;
    }

    public State getState() {
        return state;
    }

    public ChatInfoController getChatInfoController() {
        return chatInfoController;
    }

    public ChatViewController getChatViewController() {
        return chatViewController;
    }
}