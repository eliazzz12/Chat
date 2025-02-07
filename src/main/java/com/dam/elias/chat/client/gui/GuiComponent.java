package com.dam.elias.chat.client.gui;

import com.dam.elias.chat.client.api.model.ChatManagerGUI;

public abstract class GuiComponent {
    protected ChatManagerGUI cm;

    public void setChatManagerGUI(ChatManagerGUI chatManager) {
        if(chatManager == null) {
            throw new IllegalArgumentException("chatManagerGUI cannot be null");
        }
        cm = chatManager;
    }
}
