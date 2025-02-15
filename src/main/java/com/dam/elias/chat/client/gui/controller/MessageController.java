package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.MessageMediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageController implements ViewController {
    private MessageMediator mediator;
    @FXML
    private Label username;
    @FXML
    private Label text;
    @FXML
    private Label time;

    public void setMessage(Message message) {
        if(message != null) {
            User sender = message.getSender();
            username.setText(sender.getUsername());
            time.setText(message.getTimeSent());
            setStyle(message);
            text.setText(message.getText());
        }
    }

    private void setStyle(Message message) {
        if(mediator.isFromThisUser(message)) {
            ((VBox)username.getParent()).setAlignment(Pos.TOP_RIGHT);
            text.setAlignment(Pos.CENTER_RIGHT);
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
