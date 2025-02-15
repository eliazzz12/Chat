package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.gui.mediator.ChatsPreviewMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ChatsPreviewController implements ViewController {
    ChatsPreviewMediator mediator;
    List<Chat> searchedChats;
    @FXML
    TextField searchNameInput;
    @FXML
    VBox vb_chats_info;

    @FXML
    private void searchChat(ActionEvent event) {
        searchedChats = mediator.getChatsMatching(searchNameInput.getText());
        List<Node> children = vb_chats_info.getChildren();
//        try {
            //TODO convertir searchedChats en List<Parent>
//            drawChats(searchedChats);
            children.clear();
//        } catch (IOException e) {
            //TODO gestionar
//            throw new RuntimeException(e);
//        }
    }

    public void drawChats(List<Parent> items) throws IOException {
        vb_chats_info.getChildren().clear();
        for(Parent item : items) {
            vb_chats_info.getChildren().addFirst(item);
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        if(mediator == null){
            throw new IllegalArgumentException("mediator cannot be null");
        }
        this.mediator = (ChatsPreviewMediator) mediator;
    }
}
