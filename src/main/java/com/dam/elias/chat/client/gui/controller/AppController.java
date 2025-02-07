package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.GuiComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AppController extends GuiComponent {
    private static ChatViewController chatViewController;
    private static ChatsPreviewController previewController;
    private static OnlineUsersController onlineUsersController;

    @FXML
    private VBox vboxPreview;
    @FXML
    private VBox vboxChatScreen;
    @FXML
    private Label userNameLabel;


    public void setup() {
        initializeChatPreview();
        initializeChatView();
        String username = cm.getUser().getUsername();
        userNameLabel.setText("User: "+username);
    }

    private void initializeChatPreview(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chats-preview.fxml"));
        try {
            Parent item = fxmlLoader.load();
            previewController = fxmlLoader.getController();
            previewController.setChatManagerGUI(cm);
            vboxPreview.getChildren().add(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPrivateChat(Chat chat){
        try {
            previewController.addChat(chat);
        } catch (IOException e) {
            //TODO gestionar
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
        cm.send(nombreChat, text);
    }


    public void newChatMenu(MouseEvent mouseEvent) {
        //Cambiar vista chats-preview por online-users-view
        //Cambiar vista chat-view por user-info-preview
    }

    public void openChat(Chat chat) {
        chatViewController.setChat(chat);
    }

    public void updateOnlineUsers(List<User> list) {
        try {
            onlineUsersController.setUsers(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}