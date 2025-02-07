package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.GuiComponent;
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


public class ChatViewController extends GuiComponent implements ChatController {
    private Chat chat;
    @FXML
    private Label nombreChat;
    @FXML
    private VBox vboxMensajes;
    @FXML
    private TextField inputMensaje;
    @FXML
    private Button botonEnviar;

    private void enviarMensaje() {
        String mensaje = inputMensaje.getText();
        String nombre = nombreChat.getText();
        cm.send(nombre, mensaje);
    }

    //Añadir mensaje, enviar mensaje, borrar mensaje
    public void receive(Message message) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("message.fxml"));
        try {
            Parent item = fxmlLoader.load();
            MessageController messageController = fxmlLoader.getController();
            messageController.setMessage(message);
            vboxMensajes.getChildren().add(item);
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
    }


    public void setChat(Chat chat) {
        nombreChat.setText(chat.getName());
        List<Message> messages = chat.getMessageList();
        Collections.sort(messages);
        messages.forEach(this::receive);
    }
}
