package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.gui.mediator.ChatsPreviewMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatsPreviewController implements ViewController, Initializable {
    ChatsPreviewMediator mediator;
    List<Parent> searchedChats;
    @FXML
    TextField searchNameInput;
    @FXML
    VBox vb_chats_info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchNameInput.setOnKeyReleased(keyEvent -> {
            searchChat(searchNameInput.getText());
        });
    }

    private void searchChat(String search) {
        System.out.println("ChatPreviewController: Buscando chats");
        searchedChats = mediator.getChatsMatching(search);
        List<Node> children = vb_chats_info.getChildren();
        children.clear();
        try {
            drawChats(searchedChats);
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
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
