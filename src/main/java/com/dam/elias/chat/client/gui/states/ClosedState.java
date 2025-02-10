package com.dam.elias.chat.client.gui.states;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.ChatContext;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;

import javafx.scene.input.MouseEvent;

public class ClosedState extends State {

    public ClosedState(ChatContext context) {
        super(context);
    }

    @Override
    public void receiveNewMessage(Message message) {
        updateInfo(message);
        updateView(message);
    }

    @Override
    public void openChat(String chatName) {
        context.getChatViewController().setChat(context.getChat());
    }

    private void updateInfo(Message message) {
        ChatInfoController infoController = context.getChatInfoController();
        infoController.setLabel_ultimo_mensajeChat(message.getText());
        infoController.setLabel_hora_o_fechaChat(message.getTimestamp().toString());
        infoController.setLabel_num_mensajesNoLeidos(message.getChat().getUnreadMessages());
    }

    private void updateView(Message message) {
        ChatViewController chatController = context.getChatViewController();
        chatController.receive(message);
    }
}
