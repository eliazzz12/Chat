package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.GuiComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ChatInfoController extends GuiComponent implements ChatController {
    //Eventos: hacer click, cambiarUltimoMensaje(texto y hora)
    private Chat chat;
    @FXML
    private Label nombre;
    @FXML
    private Label horaFecha;
    @FXML
    private Label ultimoMensaje;
    @FXML
    private Label contadorNoLeidos;

    public void setup(Chat chat) {
        setChat(chat);
        setNombreChat(chat.getName());
        setLabel_num_mensajesNoLeidos(chat.getUnreadMessages());
    }

    @Override
    public void receive(Message message) {
        setLastMessage(message);
    }

    public void goToChat(){
        // cambiar la vista de chat por la de este chat
    }

    public void setLastMessage(Message message){
        setLabel_ultimo_mensajeChat(message.getText());
        setLabel_hora_o_fechaChat(message.getTimestamp().toString());
        setLabel_num_mensajesNoLeidos(message.getChat().getUnreadMessages());
    }

    private void setNombreChat(String chatName) {
        nombre.setText(chatName);
    }

    private void setLabel_hora_o_fechaChat(String hora_fechaChat) {
        horaFecha.setText(hora_fechaChat);
    }

    private void setLabel_ultimo_mensajeChat(String ultimo_mensajeChat) {
        ultimoMensaje.setText(ultimo_mensajeChat);
    }

    private void setLabel_num_mensajesNoLeidos(int num_mensajesChat) {
        contadorNoLeidos.setText(String.valueOf(num_mensajesChat));
    }

    public void setChat(Chat chat) {
        if(chat == null) {
            throw new IllegalArgumentException("Chat cannot be null");
        }
        this.chat = chat;
    }

    public void openChat(MouseEvent mouseEvent) {
        cm.openChat(chat);
    }
}
