package com.dam.elias.chat.client.gui.mediator;

import com.dam.elias.chat.client.api.model.Message;

public interface MessageMediator {
    boolean isFromThisUser(Message message);
}
