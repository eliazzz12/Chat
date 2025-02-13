package com.dam.elias.chat.client.api.model.states;

import com.dam.elias.chat.client.api.model.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;

public abstract class State {
    ChatContext context;

    State(ChatContext context) {
        setContext(context);
    }

    public abstract void addNewMessage(Message message);
    public abstract void openChat();

    public void updateInfo(Message message) {
        System.out.println("State: actualizando info de conversación");
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimeSent());
        infoController.setLabel_num_mensajesNoLeidos(message.getChat().getUnreadMessages());
    }

    public void setContext(ChatContext context) {
        if(context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.context = context;
    }
}
