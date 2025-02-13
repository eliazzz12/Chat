package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.mediator.ChatInfoMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.NoSuchElementException;


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
        try {
            Message lastMessage = chat.getLastMessage();
            setLabel_ultimo_mensajeChat(lastMessage.getText());
            setLabel_hora_o_fechaChat(lastMessage.getTimeSent());
        } catch (NoSuchElementException _) {
            setLabel_ultimo_mensajeChat("");
            setLabel_hora_o_fechaChat("");
        }
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
        if (num_mensajesChat > 0) {
            contadorNoLeidos.setText(String.valueOf(num_mensajesChat));
        } else {
            contadorNoLeidos.setText("");
        }
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
