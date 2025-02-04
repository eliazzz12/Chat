package com.dam.elias.chat.gui.controllers;

import com.dam.elias.chat.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChatPreviewController {
    ChatInfoController controller;
    @FXML
    VBox vb_chats_info;
    public void nuevoChat(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat-info.fxml"));
        Parent item = fxmlLoader.load();
        ChatInfoController controller = fxmlLoader.getController();
        controller.setNombreChat("Pepito");
        controller.setLabel_hora_o_fechaChat("14:30");
        controller.setLabel_num_mensajesChat(0);
        controller.setLabel_ultimo_mensajeChat("");
        vb_chats_info.getChildren().add(item);
    }

}
