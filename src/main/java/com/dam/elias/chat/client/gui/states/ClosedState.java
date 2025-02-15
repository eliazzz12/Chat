package com.dam.elias.chat.client.gui.states;

import com.dam.elias.chat.client.gui.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;

public class ClosedState extends State {

    public ClosedState(ChatContext context) {
        super(context);
    }

    @Override
    public void addNewMessage(Message message) {
        System.out.println("ClosedState: añadiendo mensaje = "+message.getText());
        updateInfo(message);
    }

    private void updateInfo(Message message) {
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimeSent());
        infoController.setLabel_num_mensajesNoLeidos(context.getChat().getUnreadMessages());
    }

    @Override
    public void openChat() {
        context.getChatViewController().setChat(context.getChat());
        State openedState = new OpenedState(context);
        context.setState(openedState);
    }
}