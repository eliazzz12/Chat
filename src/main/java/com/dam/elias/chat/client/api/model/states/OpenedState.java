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
    public void receiveNewMessage(Message message){
//        context.getChat().addMessage(message);
        updateInfoView(message);
        updateChatView(message);
    }

    private void updateInfoView(Message message) {
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimestamp().toString());
    }

    private void updateChatView(Message message) {
        ChatViewController chatController = context.getChatViewController();
        System.out.println("OpenedState: añadiendo 1 mensaje");
        chatController.receive(message);
    }

    public void openChat(String chatName) {
        // Ya está abierto, no hay que hacer nada
    }


}
