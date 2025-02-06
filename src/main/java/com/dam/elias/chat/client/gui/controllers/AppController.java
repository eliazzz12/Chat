package com.dam.elias.chat.client.gui.controllers;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.ChatManagerGUI;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController {
    private ChatManagerGUI chatManager;
    private Map<Chat, List<ChatController>> chatControllers = new HashMap<>();
    private static ChatViewController chatViewController;
    private static ChatsPreviewController previewController;

    @FXML
    private VBox vboxPreview;
    @FXML
    private VBox vboxChatScreen;
    @FXML
    private Label userNameLabel;


    public void initialize(String username) {
        initializeChatPreview();
        initializeChatView();
        userNameLabel.setText("User: "+username);
    }

    private void initializeChatPreview(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chats-preview.fxml"));
        try {
            Parent item = fxmlLoader.load();
            previewController = fxmlLoader.getController();
            vboxPreview.getChildren().add(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeChatView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat-view.fxml"));
        try {
            Parent item = fxmlLoader.load();
            chatViewController = fxmlLoader.getController();
            vboxChatScreen.getChildren().add(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void noConnection(Exception e) {
        //TODO implementar que ocurre cuando no puede conectarse
        System.out.println("No connection: " + e.getMessage());
    }

    public void sendError(Exception e) {
        //TODO implementar que ocurre cuando no se puede enviar
    }

    public void send(String nombreChat, String text) {
        chatManager.send(nombreChat, text);
    }

    public boolean receive(Message message, Chat chat) {
        chatControllers.get(chat).forEach(c -> c.receive(message, chatManager.getUser()));
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setChatManager(ChatManagerGUI chatManager) {
        this.chatManager = chatManager;
    }

    public void newPrivateChat(User user){

    }
}