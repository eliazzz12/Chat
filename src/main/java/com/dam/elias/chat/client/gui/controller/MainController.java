package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.api.connection.SendList;
import com.dam.elias.chat.client.api.connection.SendMessage;
import com.dam.elias.chat.client.api.model.*;
import com.dam.elias.chat.client.api.model.states.ClosedState;
import com.dam.elias.chat.client.gui.mediator.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

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
    @FXML
    private Button online_users_button;

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

    public void newChat(Chat chat){
        System.out.println("MainController: creando newChat");
        try {
            FXMLLoader fxmlLoaderInfo = new FXMLLoader(App.class.getResource("chat-info.fxml"));
            Parent infoItem = fxmlLoaderInfo.load();
            ChatInfoController infoController = fxmlLoaderInfo.getController();
            infoController.setMediator(this);
            infoController.setup(chat);
            ChatContext context = new ChatContext(chat, infoItem, infoController, chatViewController);
            context.setState(new ClosedState(context));
            contexts.put(chat.getName(), context);
            updatePreviewChats();
        } catch (IOException e) {
            //TODO gestionar
            throw new RuntimeException(e);
        }
    }

    private List<Parent> getInfoItemList() {
        List<Parent> list = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();

        contexts.values().forEach(chatContext -> chats.add(chatContext.getChat()));
        Collections.sort(chats);

        for(Chat chat : chats) {
            list.add(contexts.get(chat.getName()).getInfoItem());
        }

        return list;
    }

    public void receiveNewMessage(Message message) {
        System.out.println("MainController: recibiendo mensaje");
        String chatName= message.getChat().getName();
        String senderName = message.getSender().getUsername();
        String chatKey;
        Chat chat = message.getChat();
        if(!contexts.containsKey(chatName) && !contexts.containsKey(senderName)) {
            System.out.println("MainController: creando nuevo contexto");
            if(chat.isPrivate()){
                newPrivateChat(message.getSender());
            } else {
                newGroupChat(chat.getName(), ((GroupChat) chat).getUsers());
            }
        }
        /*
            Separado del if anterior porque tiene que comprobarse existiera o no contexto.
         */
        if(chat.isPrivate()){
            chatKey = senderName;
        } else {
            chatKey = chatName;
        }

        ChatContext context = contexts.get(chatKey);
        context.getChat().addMessage(message);

        Platform.runLater(() -> {
            context.getState().addNewMessage(message);
        });
        updatePreviewChats();
    }

    private void updatePreviewChats() {
        Platform.runLater(() -> {
            System.out.println("MainController: updatePreviewChats");
            try {
                previewController.drawChats(getInfoItemList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onlineUsersMenu(MouseEvent mouseEvent) {
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
        online_users_button.setText("Update");
    }

    @Override
    public void setChatView() {
        online_users_button.setText("Online Users");
        vboxPreview.getChildren().clear();
        vboxChatScreen.getChildren().clear();
        vboxPreview.getChildren().add(chatPreview);
        vboxChatScreen.getChildren().add(chatView);
    }

    @Override
    public User getUser() {
        return user;
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
        context.getState().openChat();
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

    public void sendMessage(String chatName, String text) {
        System.out.println("MainController: enviando mensaje "+text);
        ChatContext context = contexts.get(chatName);
        if(context != null) {
            Chat chat = context.getChat();
            Message message = new Message(user, chat, text);
            message.addToChat();
            context.getState().addNewMessage(message);
            SendMessage sendMessage = new SendMessage(connection.getOut(), message);
            new Thread(sendMessage).start();
            updatePreviewChats();
        }
    }

    /**
     * Searches chat names.
     *
     * @param search the search text
     * @return a list with Chats containing the search in their name
     */
    @Override
    public List<Parent> getChatsMatching(String search) {
        List<Parent> list = new ArrayList<>();
        System.out.println("Chats encontrados:");
        contexts.forEach((k,v) -> {
            System.out.println("Key: "+k+". Search: "+search);
            if(containsIgnoreCase(k, search)) {
                list.add(v.getInfoItem());
                System.out.print(v.getChat().getName()+" | ");
            }
        });
        return list;
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
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