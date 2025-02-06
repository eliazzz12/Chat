package com.dam.elias.chat.client.gui.controllers;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.ChatManagerGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;

public class ChatsPreviewController {
    ChatInfoController controller;

    @FXML
    TextField tf_busqueda;
    @FXML
    VBox vb_chats_info;

    public void nuevoChat(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat-info.fxml"));
        Parent item = fxmlLoader.load();
        ChatInfoController controller = fxmlLoader.getController();
        //Decirle a controller que tiene que poner
        vb_chats_info.getChildren().add(item);
    }

}
