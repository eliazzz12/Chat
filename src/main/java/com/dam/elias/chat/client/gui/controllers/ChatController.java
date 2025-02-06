package com.dam.elias.chat.client.gui.controllers;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;

public interface ChatController {
    void receive(Message message, User user);
}
