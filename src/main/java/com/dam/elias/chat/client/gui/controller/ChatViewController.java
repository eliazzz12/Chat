package com.dam.elias.chat.client.gui.controllers;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.gui.GuiComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatViewController extends GuiComponent implements ChatController {
    private Chat chat;
    private User otherUser;
    @FXML
    private Label nombreChat;
    @FXML
    private VBox vboxMensajes;
    @FXML
    private TextField inputMensaje;
    @FXML
    private Button botonEnviar;

    private void enviarMensaje(String mensaje) {
        String nombre = nombreChat.getText();
        rootController.send(nombre, mensaje);
    }

    //Añadir mensaje, enviar mensaje, borrar mensaje
    public void receive(Message message, User user) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("message.fxml"));
        try {
            Parent item = fxmlLoader.load();
            MessageController messageController = fxmlLoader.getController();

            messageController.setMessage(message, user);
            vboxMensajes.getChildren().add(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootController = new FXMLLoader(App.class.getResource("main-view.fxml")).getController();
    }
}
