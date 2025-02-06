package com.dam.elias.chat.client.api.connection;

import com.dam.elias.chat.client.api.model.*;
import com.dam.elias.chat.server.exceptions.HandlerNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class ReceiverClient implements Runnable {
    private static ChatManagerGUI chatManagerGUI;
    private ObjectInputStream in;

    public ReceiverClient(ChatManagerGUI chatManagerGUI, ObjectInputStream in) {
        setChatManagerGUI(chatManagerGUI);
        setIn(in);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                handle(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                Message message = (Message) in.readObject();
                chatManagerGUI.receive(message);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private interface Handler {
        void handle(Object o);
    }

    private static final Map<Class, ReceiverClient.Handler> dispatch = Map.ofEntries(
            Map.entry(Message.class, o -> handleMessage((Message) o)),
            Map.entry(User.class, o -> handleUser((User) o)),
            Map.entry(Chat.class, o -> handleChat((Chat) o)),
            Map.entry(Object[].class, o -> handleObjectArray((Object[]) o))
    );

    private static void handle(Object o) {
        ReceiverClient.Handler h = dispatch.get(o.getClass());
        if (h == null) {
            throw new HandlerNotFoundException("Handler not found for " + o.getClass()+ " class");
        }
        h.handle(o);
    }

    static void handleMessage(Message message){
        chatManagerGUI.receive(message);
    }

    static void handleUser(User userToUpdate){
        throw new UnsupportedOperationException("Not implemented yet");
        //TODO updateUser(): actualizar datos en todos los clientes que tengan conversación con el user
    }

    static void handleObjectArray(Object[] array){
        throw new UnsupportedOperationException("Not implemented yet");
//        boolean exists = false;
//        User askingUser = (User) array[0];
//        Class searchedClass = (Class) array[1];
//        String searchedName = (String) array[2];
//
//        if(searchedClass == User.class) {
//            exists = userExists(searchedName);
//        } else if(searchedClass == GroupChat.class) {
//            exists = groupChatExists(searchedName);
//        }
//
//        users.get(askingUser).sendUserStatus(exists);
    }

    static void handleChat(Chat chat){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void setChatManagerGUI(ChatManagerGUI chatManagerGUI_) {
        if(chatManagerGUI == null) {
            throw new IllegalArgumentException("chatManagerGUI cannot be null null");
        }
        this.chatManagerGUI = chatManagerGUI_;
    }

    private void setIn(ObjectInputStream in) {
        if(in == null){
            throw new IllegalArgumentException("Object input stream can't be null");
        }
        this.in = in;
    }
}
