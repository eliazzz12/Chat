package com.dam.elias.chat.client.api.model;

import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.api.connection.ReceiverClient;
import com.dam.elias.chat.client.api.connection.SendMessage;
import com.dam.elias.chat.client.api.model.exceptions.ChatManagerGUINotCreatedException;
import com.dam.elias.chat.client.gui.controllers.AppController;
import javafx.application.Application;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class ChatManagerGUI extends Connection implements ChatApi, Initializable  {
    private static ChatManagerGUI instance;
    private AppController controller;
    private User user;
    private Map<String, Chat> chats = new HashMap<>();

    private ChatManagerGUI(Application.Parameters param, AppController controller) throws IOException {
        setup(param);
        setController(controller);
        connect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initializing");
        createReceiver();
    }

    public static ChatManagerGUI getInstance(Application.Parameters param, AppController controller) throws IOException {
        ChatManagerGUI result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ChatManagerGUI.class) {
            if (instance == null) {
                instance = new ChatManagerGUI(param, controller);
            }
            return instance;
        }
    }

    public static ChatManagerGUI getInstance() throws ChatManagerGUINotCreatedException {
        ChatManagerGUI result = instance;
        if (result != null) {
            return result;
        } else {
            throw new ChatManagerGUINotCreatedException("No ChatManagerGUI instance found");
        }

    }

    private void createReceiver() {
        new Thread(new ReceiverClient(this, in)).start();
    }

    @Override
    public void send(String chatName, String text) {
        Chat chat = chats.get(chatName);
        Message message = new Message(user, chat, text);
        SendMessage sendMessage = new SendMessage(out, chat, message);
        new Thread(sendMessage).start();
    }

    /**
     * Receives a message from the server
     */
    @Override
    public void receive(Message message) {
        Chat chat = message.getChat();
        boolean isPrivate = chat.isPrivate();

        if(!chats.containsKey(chat.getName())) {
            if (isPrivate) {
                newPrivateChat((PrivateChat) chat);
            } else {
                newGroupChat((GroupChat) chat);
            }
        }
        controller.receive(message, chat);
    }

    private boolean existsChat(String name){
        return chats.containsKey(name);
    }

    /**
     * Creates a new PrivateChat. The other user iniciated this chat
     *
     * @param privateChat the new PrivateChat
     */
    @Override
    public void newPrivateChat(PrivateChat privateChat) {
        addChat(privateChat);
        //TODO informar al controlador
    }

    /**
     * Tells to the AppController that a new PrivateChat has been created
     *
     * @param user the user the new PrivateChat is with
     */
    @Override
    public PrivateChat newPrivateChat(User user) {
        //TODO informar al controlador
        if(!existsChat(user.getUsername())) {
            controller.newPrivateChat(user);
        }
        return null;
    }

    /**
     * Tells to the AppController that a new GroupChat has been created
     *
     * @param users the list of users included in this group chat
     * @return a new GroupChat
     */
    @Override
    public GroupChat newGroupChat(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO informar al controlador
    }

    /**
     * Adds a new GroupChat this user has been added to
     *
     * @param groupChat the new GroupChat
     */
    @Override
    public void newGroupChat(GroupChat groupChat) {
        addChat(groupChat);
        //TODO informar al controlador
    }

    /**
     * Gets the last message from a chat
     *
     * @param chat the chat to retrieve from
     * @return the last message sent in the chat
     */
    @Override
    public Message getLastMessage(Chat chat) {
        return null;
    }

    /**
     * Checks whether a message has been sent to the server
     *
     * @param message the message to check
     * @param chat    the chat this message was sent to
     * @return true if the message was sent to the server, false otherwise
     */
    @Override
    public boolean messageSentStatus(Message message, Chat chat) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    /**
     * Checks whether a message has been received by the other client
     *
     * @param message the message to check
     * @param chat    the chat this message was sent to
     * @return true if the message was received by the other client, false otherwise
     */
    @Override
    public boolean messageReceivedStatus(Message message, PrivateChat chat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Checks whether a message has been received by the other clients
     *
     * @param message the message to check
     * @param chat    the chat this message was sent to
     * @return a Map with the group users' and true if the message was received by the client, false otherwise
     */
    @Override
    public Map<User, Boolean> messageReceivedStatus(Message message, GroupChat chat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void addChat(Chat chat) {
        String name;
        if(chat.isPrivate()) {

        }
        chats.put(chat.getName(), chat);
    }

    public boolean doesUserExist(String username) throws IOException {
        Object[] array = {user, User.class, username};
        out.writeObject(array);
        return in.readBoolean();
    }

    private void setAddress(InetAddress address) {
        if(address == null) {
            throw new IllegalArgumentException("address cannot be null");
        }
        this.address = address;
    }

    private void setPort(int port) {
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("invalid port (" + port + ")");
        }
        this.port = port;
    }

    private void setController(AppController controller) {
        if(controller == null) {
            throw new IllegalArgumentException("controller cannot be null");
        }
        this.controller = controller;
    }

    public User getUser() {
        return user;
    }
}
