package com.dam.elias.chat.client.api.model.states;

import com.dam.elias.chat.client.api.model.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;

public class OpenedState extends State {
    OpenedState(ChatContext context) {
        super(context);
    }

    @Override
    public void addNewMessage(Message message){
//        context.getChat().addMessage(message);
        updateInfo(message);
        updateChatView(message);
    }

    private void updateChatView(Message message) {
        ChatViewController chatController = context.getChatViewController();
        System.out.println("OpenedState: añadiendo 1 mensaje");
        chatController.receive(message);
    }


    public void openChat() {
        // Ya está abierto, no hay que hacer nada
    }


}
