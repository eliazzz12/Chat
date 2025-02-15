package com.dam.elias.chat.client.api.model.states;

import com.dam.elias.chat.client.api.model.ChatContext;
import com.dam.elias.chat.client.api.model.Message;

public abstract class State {
    ChatContext context;

    State(ChatContext context) {
        setContext(context);
    }

    public abstract void addNewMessage(Message message);
    public abstract void openChat();

    public void setContext(ChatContext context) {
        if(context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.context = context;
    }
}
