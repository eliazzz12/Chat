package com.dam.elias.chat.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ChatInfoController {

    @FXML
    Label nombre;
    @FXML
    Label horaFecha;
    @FXML
    Label ultimoMensaje;
    @FXML
    Label contadorNoLeidos;


    public void setNombreChat(String nombreChat) {
        nombre.setText(nombreChat);
    }

    public void setLabel_hora_o_fechaChat(String hora_fechaChat) {
        horaFecha.setText(hora_fechaChat);
    }

    public void setLabel_ultimo_mensajeChat(String ultimo_mensajeChat) {
        ultimoMensaje.setText(ultimo_mensajeChat);
    }

    public void setLabel_num_mensajesChat(int num_mensajesChat) {
        contadorNoLeidos.setText(String.valueOf(num_mensajesChat));
    }
}
