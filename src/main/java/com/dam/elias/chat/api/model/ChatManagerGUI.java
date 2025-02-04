package com.dam.elias.chat.api.model;

import com.dam.elias.chat.gui.controllers.AppController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class ChatManagerGUI implements ChatApi, Runnable {
    private InetAddress address;
    private int port;
    private Socket server;
    private AppController controller;


    public ChatManagerGUI(InetAddress address, int port, AppController controller) {
        setController(controller);
        setAddress(address);
        setPort(port);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        System.out.println("hola mundillo desde el run");
    }

    @Override
    public void connect() throws IOException {
        System.out.println("Conectando...");
        server = new Socket(address, port);
        System.out.println("seversocket: "+server.getInetAddress().getHostAddress());
    }

    @Override
    public void disconnect() throws IOException {
        server.close();
    }

    @Override
    public boolean send(Message message, Chat chat) {
        return false;
    }

    @Override
    public void receive(Message message, Chat chat) {

    }

    @Override
    public PrivateChat newPrivateChat(User user) {
        return null;
    }

    @Override
    public GroupChat newGroupChat(List<User> users) {
        return null;
    }

    @Override
    public Message getLastMessage(Chat chat) {
        return chat.getLastMessage();
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
}
