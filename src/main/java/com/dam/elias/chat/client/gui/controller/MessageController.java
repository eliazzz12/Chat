package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.MessageMediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/*
    controlo el texto, quien envia(para saber a que lado poner la burbuja del mensaje)
 */
public class MessageController implements ViewController {
    private MessageMediator mediator;
    @FXML
    private Label username;
    @FXML
    private Label text;

    public void setMessage(Message message) {
        if(message != null) {
            User sender = message.getSender();
            username.setText(sender.getUsername());
            text.setText(message.getText());
            setStyle(message);
        }
    }

    private void setStyle(Message message) {
        if(mediator.isFromThisUser(message)) {
            //Poner nombre de usuario a la derecha
//            username.setLayoutX(100);
            System.out.println("YO: "+message.getText());
        } else {
            //Poner nombre de usuario a la izquierda
//            username.setLayoutX(0);
            System.out.println("OTRO: "+message.getText());
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        if(mediator == null) {
            throw new IllegalArgumentException("mediator cannot be null");
        }
        this.mediator = (MessageMediator) mediator;
    }
}
