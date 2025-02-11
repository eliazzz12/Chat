package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.mediator.ChatViewMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.MessageMediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class ChatViewController implements ViewController {
    private ChatViewMediator mediator;
    @FXML
    private Label nombreChat;
    @FXML
    private VBox vboxMensajes;
    @FXML
    private TextField inputMensaje;
    @FXML
    private Button botonEnviar;

    @FXML
    private void sendMessage() {
        System.out.println("ChatViewController: enviando mensaje "+inputMensaje.getText());
        String name = nombreChat.getText();
        String message = inputMensaje.getText();
        mediator.sendMessage(name, message);
    }

    //Añadir mensaje, enviar mensaje, borrar mensaje
    public void receive(Message message) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("message.fxml"));
        try {
            Parent item = fxmlLoader.load();
            MessageController messageController = fxmlLoader.getController();
            messageController.setMediator((Mediator)mediator);
            messageController.setMessage(message);
            //Platform
            Platform.runLater(() -> {
                System.out.println("ChatViewController: añadiendo mensaje "+message.getText());
                vboxMensajes.getChildren().add(item);
            });
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
    }


    public void setChat(Chat chat) {
        vboxMensajes.getChildren().clear();
        nombreChat.setText(chat.getName());
        List<Message> messages = chat.getMessageList();
        if(messages != null) {
            Collections.sort(messages);
            System.out.println("ChatViewController: añadiendo TODOS los mensajes");
            System.out.println("ChatViewController: nume mensajes = "+messages.size());
            messages.forEach(this::receive);
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        if(mediator == null) {
            throw new IllegalArgumentException("mediator cannot be null");
        }
        this.mediator = (ChatViewMediator) mediator;
    }
}
