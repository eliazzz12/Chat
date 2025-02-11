package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.gui.mediator.ChatInfoMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ChatInfoController implements ViewController {
    //Eventos: hacer click, cambiarUltimoMensaje(texto y hora)
    private ChatInfoMediator mediator;

    @FXML
    private Label nombre;
    @FXML
    private Label horaFecha;
    @FXML
    private Label ultimoMensaje;
    @FXML
    private Label contadorNoLeidos;

    public void setup(Chat chat) {
        setNombreChat(chat.getName());
        setLabel_num_mensajesNoLeidos(chat.getUnreadMessages());
    }

    private void setNombreChat(String chatName) {
        nombre.setText(chatName);
    }

    public void setLabel_hora_o_fechaChat(String hora_fechaChat) {
        horaFecha.setText(hora_fechaChat);
    }

    public void setLabel_ultimo_mensajeChat(String ultimo_mensajeChat) {
        ultimoMensaje.setText(ultimo_mensajeChat);
    }

    public void setLabel_num_mensajesNoLeidos(int num_mensajesChat) {
        contadorNoLeidos.setText(String.valueOf(num_mensajesChat));
    }

    public void openChat(MouseEvent mouseEvent) {
        mediator.openChat(nombre.getText());
    }

    @Override
    public void setMediator(Mediator mediator) {
        if(mediator == null){
            throw new IllegalArgumentException("mediator can not be null");
        }
        this.mediator = (ChatInfoMediator) mediator;
    }
}
