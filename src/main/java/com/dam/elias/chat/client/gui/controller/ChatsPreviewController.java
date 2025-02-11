package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.gui.mediator.ChatsPreviewMediator;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ChatsPreviewController implements ViewController {
    ChatsPreviewMediator mediator;
    List<Chat> searchedChats;
    Set<Chat> chats = new HashSet<>();
    @FXML
    TextField searchNameInput;
    @FXML
    VBox vb_chats_info;

    //haHechoClick()
    /*
        enviar al mediador
     */

    @FXML
    private void searchChat(ActionEvent event) {
        searchedChats = mediator.getChatsMatching(searchNameInput.getText());
        List<Node> children = vb_chats_info.getChildren();
        try {
            drawChats(searchedChats);
            children.clear();
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
    }

    private void drawChats(List<Chat> chats) throws IOException {
        Collections.sort(chats);
        for(Chat chat : chats) {
            drawChat(chat);
        }
    }

    private void drawChat(Chat chat) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat-info.fxml"));
        Parent item = fxmlLoader.load();
        ChatInfoController controller = fxmlLoader.getController();
        controller.setup(chat);
        vb_chats_info.getChildren().add(0, item);
    }

    //TODO al mediador
    public void addChat(Chat chat) {
        chats.add(chat);
        //Así se obtiene una Lista mutable. Usando .stream().toList() la lista es inmutable y no sirve
        ArrayList<Chat> list = (ArrayList<Chat>) chats.stream().collect(Collectors.toList());
        try {
            drawChats(list);
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
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
