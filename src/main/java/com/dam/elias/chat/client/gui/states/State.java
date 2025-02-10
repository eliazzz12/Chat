package com.dam.elias.chat.client.gui.states;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.ChatContext;

import javafx.scene.input.MouseEvent;

public abstract class State {
    ChatContext context;

    State(ChatContext context) {
        setContext(context);
    }

    public abstract void receiveNewMessage(Message message);
    public abstract void openChat(String chatName);

    public void setContext(ChatContext context) {
        if(context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.context = context;
    }
}
