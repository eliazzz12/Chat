package com.dam.elias.chat.client.api.model;

import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.api.connection.ReceiverClient;
import com.dam.elias.chat.client.api.connection.SendList;
import com.dam.elias.chat.client.api.connection.SendMessage;
import com.dam.elias.chat.client.api.model.exceptions.ChatManagerGUINotCreatedException;
import com.dam.elias.chat.client.gui.controller.AppController;
import com.dam.elias.chat.client.gui.controller.ChatInfoController;
import com.dam.elias.chat.client.gui.controller.ChatViewController;
import javafx.application.Application;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ChatManagerGUI extends Connection implements ChatApi, Initializable  {
    private static ChatManagerGUI instance;
    private AppController controller;
    private User user;
    private Map<String, Chat> chats = new HashMap<>();
    private Map<Chat, ChatViewController> chatViewControllers = new HashMap<>();
    private Map<Chat, ChatInfoController> chatInfoControllers = new HashMap<>();

    private ChatManagerGUI(Application.Parameters param, AppController controller, User user) throws IOException {
        setup(param);
        setController(controller);
        setUser(user);
        connect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createReceiver();
    }

    public static ChatManagerGUI getInstance(Application.Parameters param, AppController controller, User user) throws IOException {
        ChatManagerGUI result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ChatManagerGUI.class) {
            if (instance == null) {
                instance = new ChatManagerGUI(param, controller, user);
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
        SendMessage sendMessage = new SendMessage(out, message);
        new Thread(sendMessage).start();
    }

    /**
     * Receives a message from the server
     */
    @Override
    public void receive(Message message) {
        Chat chat = message.getChat();
        if(!chats.containsKey(chat.getName())) {
            newPrivateChat(message);
        }
        chatInfoControllers.get(chat).receive(message);
        chatViewControllers.get(chat).receive(message);
    }

    private boolean existsChat(String name){
        return chats.containsKey(name);
    }

    @Override
    public void newPrivateChat(Message message) {
        PrivateChat chat = new PrivateChat(this.user, message.getSender());
        addChat(chat);
        controller.newPrivateChat(chat);
    }

    @Override
    public void newPrivateChat(User user) {
        PrivateChat chat = new PrivateChat(this.user, user);
        addChat(chat);
        controller.newPrivateChat(chat);
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

    @Override
    public void openChat(Chat chat) {
        controller.openChat(chat);
    }

    @Override
    public void askForOnlineUsers() {
        List<User> list = new ArrayList<>();
        list.add(user);
        SendList thread = new SendList(out, list);
        new Thread(thread).start();
    }

    @Override
    public void updateUserList(List<User> list){
        controller.updateOnlineUsers(list);
    }

    private void addChat(Chat chat) {
        String name;
        if(chat.isPrivate()) {
            name = ((PrivateChat) chat).getOtherUser(user).getUsername();
        } else {
            name = chat.getName();
        }
        chats.put(name, chat);
    }

    public boolean doesUserExist(String username) throws IOException {
        Object[] array = {user, User.class, username};
        out.writeObject(array);
        return in.readBoolean();
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

    public ChatViewController getChatViewController(Chat chat) {
        return chatViewControllers.get(chat);
    }
    public ChatInfoController getChatInfoController(Chat chat) {
        return chatInfoControllers.get(chat);
    }

    public List<Chat> getChatsMatching(String text) {
        List<Chat> list = new ArrayList<>();
        chats.forEach((k,v) -> {
            if(k.contains(text)) {
                list.add(v);
            }
        });
        return list;
    }

    public void setUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        this.user = user;
    }
}
