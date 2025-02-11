package com.dam.elias.chat.client.api.model.states;

import com.dam.elias.chat.client.api.model.ChatContext;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;

public class ClosedState extends State {

    public ClosedState(ChatContext context) {
        super(context);
    }

    @Override
    public void receiveNewMessage(Message message) {
//        context.getChat().addMessage(message);
        updateInfo(message);
    }

    @Override
    public void openChat(String chatName) {
        context.getChatViewController().setChat(context.getChat());
        State newState = new OpenedState(context);
        context.setState(newState);
//        context.getChat().setAllRead();
        System.out.println("ClosedState: ahora estoy abierto");
    }

    private void updateInfo(Message message) {
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimestamp().toString());
        infoController.setLabel_num_mensajesNoLeidos(message.getChat().getUnreadMessages());
    }
}
