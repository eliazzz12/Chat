package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Message;

public interface ChatController {
    void receive(Message message);
}
