package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.api.connection.SendList;
import com.dam.elias.chat.client.api.connection.SendMessage;
import com.dam.elias.chat.client.api.model.*;
import com.dam.elias.chat.client.gui.ChatContext;
import com.dam.elias.chat.client.gui.mediator.*;
import com.dam.elias.chat.client.gui.states.ClosedState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController implements ChatViewMediator, Mediator, ChatsPreviewMediator,
        OnlineUsersMediator, ChatInfoMediator, MessageMediator {
    private User user;
    private Connection connection;
    private static Parent chatPreview;
    private static Parent chatView;
    private static ChatViewController chatViewController;
    private static ChatsPreviewController previewController;
    private static OnlineUsersController onlineUsersController;
    private Map<String, ChatContext> contexts = new HashMap<>();
    private List<User> onlineUsers;

    @FXML
    private VBox vboxPreview;
    @FXML
    private VBox vboxChatScreen;
    @FXML
    private Label userNameLabel;

    public void setConnection(Connection connection) {
        if(connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
    }

    public void setup() {
        initializeChatPreview();
        initializeChatView();
        String username = user.getUsername();
        userNameLabel.setText("User: "+username);
    }

    private void initializeChatPreview(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chats-preview.fxml"));
        try {
            chatPreview = fxmlLoader.load();
            previewController = fxmlLoader.getController();
            previewController.setMediator(this);
            vboxPreview.getChildren().add(chatPreview);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeChatView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chat-view.fxml"));
        try {
            chatView = fxmlLoader.load();
            chatViewController = fxmlLoader.getController();
            chatViewController.setMediator(this);
            vboxChatScreen.getChildren().add(chatView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void newPrivateChat(User user){
        PrivateChat chat = new PrivateChat(this.user, user);
        newChat(chat);
    }

    @Override
    public void newGroupChat(String name, List<User> users){
        GroupChat chat = new GroupChat(name, users);
        newChat(chat);
    }

    @Override
    public void newGroupChat(GroupChat chat){
        newChat(chat);
    }

    public void newChat(Chat chat){
        //TODO generar el contexto de la conversación (chat, Parent, estado, controladores (2))
        try {
            //FIXME Necesita de los dos items?
            FXMLLoader fxmlLoaderInfo = new FXMLLoader(App.class.getResource("chat-info.fxml"));
            Parent infoItem = fxmlLoaderInfo.load();

            ChatInfoController infoController = fxmlLoaderInfo.getController();
//            ChatContext context = new ChatContext(chat, infoItem, chatViewItem, infoController, viewController);
            ChatContext context = new ChatContext(chat, infoController, chatViewController);
            context.setState(new ClosedState(context));

            contexts.put(chat.getName(), context);

            Platform.runLater(() -> {
                try {
                    previewController.drawChats(getChatList());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
    }

    private List<Chat> getChatList() {
        List<Chat> list = new ArrayList<>();
        contexts.values().forEach(chatContext -> {
            list.add(chatContext.getChat());
        });

        return list;
    }

    public void receiveNewMessage(Message message) {
        Chat chat = message.getChat();
        chat.setName(message.getSender().getUsername());
        ChatContext context = contexts.get(chat.getName());
        if(context == null) {
            if(chat.isPrivate()){
                newPrivateChat(message.getSender());
            } else {
                newGroupChat((GroupChat) chat);
            }
            newChat(chat);
            context = contexts.get(chat.getName());
        }
        context.getState().receiveNewMessage(message);
    }


    public void newChatMenu(MouseEvent mouseEvent) {
        askForOnlineUsers();
        while(onlineUsers == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //Cambiar vista chats-preview por online-users-view
        setOnlineUsersView();
        //Cambiar vista chat-view por user-info-preview
        setOnlineUsersInfoPreview();
    }

    @Override
    public void setChatView() {
        vboxPreview.getChildren().clear();
        vboxChatScreen.getChildren().clear();
        vboxPreview.getChildren().add(chatPreview);
        vboxChatScreen.getChildren().add(chatView);
    }

    private void setOnlineUsersView() {
        vboxPreview.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("online-users-view.fxml"));
        try {
            Parent item = fxmlLoader.load();
            onlineUsersController = fxmlLoader.getController();
            onlineUsersController.setMediator(this);
            vboxPreview.getChildren().add(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openChat(String chatName) {
        ChatContext context = contexts.get(chatName);
        context.setChatViewController(chatViewController);
        closeChats();
        context.getState().openChat(chatName);
    }

    public void closeChats() {
        contexts.values().forEach(context -> {
           context.setState(new ClosedState(context));
        });
    }

    private void setOnlineUsersInfoPreview() {
        vboxChatScreen.getChildren().clear();
        try {
            onlineUsersController.setUsers(onlineUsers);
        } catch (IOException e) {
            // TODO gestionar
            throw new RuntimeException(e);
        }
    }

    public void askForOnlineUsers() {
        List<User> list = new ArrayList<>();
        list.add(user);
        SendList thread = new SendList(connection.getOut(), list);
        new Thread(thread).start();
    }

    public void updateOnlineUsers(List<User> list) {
        onlineUsers = list;
    }

    public void noConnection(Exception e) {
        //TODO implementar que ocurre cuando no puede conectarse
        System.out.println("No connection: " + e.getMessage());
    }

    public void sendError(Exception e) {
        //TODO implementar que ocurre cuando no se puede enviar
    }

    public void sendMessage(String chatName, String text) {
        System.out.println("MainController: enviando mensaje "+text);
        ChatContext context = contexts.get(chatName);
        if(context != null) {
            Chat chat = context.getChat();
            Message message = new Message(user, chat, text);
            message.addToChat();
            context.getState().receiveNewMessage(message);
            SendMessage sendMessage = new SendMessage(connection.getOut(), message);
            new Thread(sendMessage).start();
        }
    }

    /**
     * Searches chat names.
     *
     * @param text the search text
     * @return a list with Chats containing the search in their name
     */
    @Override
    public List<Chat> getChatsMatching(String text) {
        List<Chat> list = new ArrayList<>();
        contexts.forEach((k,v) -> {
            if(k.contains(text)) {
                list.add(v.getChat());
            }
        });
        return list;
    }

    public void setUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    @Override
    public boolean isFromThisUser(Message message) {
        return message.getSender().equals(user);
    }
}