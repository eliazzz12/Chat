package com.dam.elias.chat.client.api.model.states;

import com.dam.elias.chat.client.api.model.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;

import java.time.format.DateTimeFormatter;

public class ClosedState extends State {

    public ClosedState(ChatContext context) {
        super(context);
    }

    @Override
    public void addNewMessage(Message message) {
        System.out.println("ClosedState: añadiendo mensaje = "+message.getText());
        updateInfo(message);
    }

    @Override
    public void openChat() {
        context.getChatViewController().setChat(context.getChat());
        State openedState = new OpenedState(context);
        context.setState(openedState);
//        context.getChat().setAllRead();
        System.out.println("ClosedState: ahora estoy abierto");
    }
}