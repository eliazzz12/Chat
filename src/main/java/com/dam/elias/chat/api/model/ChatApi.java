package com.dam.elias.chat.api.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public interface ChatApi {
    void connect() throws IOException;
    void disconnect() throws IOException;
    boolean send(Message message, Chat chat);
    void receive(Message message, Chat chat);
    PrivateChat newPrivateChat(User user);
    GroupChat newGroupChat(List<User> users);
    Message getLastMessage(Chat chat);
}