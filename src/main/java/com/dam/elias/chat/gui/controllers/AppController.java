package com.dam.elias.chat.gui.controllers;

import com.dam.elias.chat.api.model.Chat;
import com.dam.elias.chat.api.model.ChatManagerGUI;
import com.dam.elias.chat.api.model.Message;
import com.dam.elias.chat.api.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController {
    private ChatManagerGUI chatManager;
    private Map<Chat, List<ChatController>> chatControllers = new HashMap<>();

    public void noConnection(Exception e) {
        //TODO implementar que ocurre cuando no puede conectarse
        System.out.println("No connection: " + e.getMessage());
    }

    public void sendError(Exception e) {
        //TODO implementar que ocurre cuando no se puede enviar
    }

    public void send(User sender, User receiver, Chat chat, String text) {
        chatManager.send(sender, receiver, chat, text);
    }

    public boolean receive(Message message, Chat chat) {
        String senderName = message.getSender().getUsername();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setChatManager(ChatManagerGUI chatManager) {
        this.chatManager = chatManager;
    }
}