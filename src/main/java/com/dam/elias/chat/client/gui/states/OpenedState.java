package com.dam.elias.chat.client.gui.states;

import com.dam.elias.chat.client.gui.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;

public class OpenedState extends State {
    OpenedState(ChatContext context) {
        super(context);
        context.getChat().setAllRead();
        context.getChatInfoController().setLabel_num_mensajesNoLeidos(0);
    }

    @Override
    public void addNewMessage(Message message){
        updateInfo(message);
        updateChatView(message);
        context.getChat().setAllRead();
    }

    private void updateInfo(Message message) {
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimeSent());
    }

    private void updateChatView(Message message) {
        ChatViewController chatController = context.getChatViewController();
        System.out.println("OpenedState: añadiendo 1 mensaje");
        chatController.receive(message);
    }


    public void openChat() {
        // Ya está abierto, no hay que hacer nada
        System.out.println("OpenedState: ya estoy abierto");
    }
}
