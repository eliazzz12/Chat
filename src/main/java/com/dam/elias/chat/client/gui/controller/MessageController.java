package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/*
    controlo el texto, quien envia(para saber a que lado poner la burbuja del mensaje)
 */
public class MessageController {
    @FXML
    private Image profilePicture;
    @FXML
    private Label username;
    @FXML
    private Label text;
    @FXML
    private HBox textBox;

    public void setMessage(Message message) {
        User sender = message.getSender();
        profilePicture = sender.getProfilePicture();
        username.setText(sender.getUsername());
        text.setText(message.getText());
        setStyle(message, sender);
    }

    private void setStyle(Message message, User sender) {
        if(message.getSender().equals(sender)) {
            //Poner imagen a la derecha
            text.toFront();
        } else {
            //Poner imagen a la izquierda
            text.toBack();
        }
    }


}
