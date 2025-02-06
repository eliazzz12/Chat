package com.dam.elias.chat.server;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.api.model.exceptions.UsernameBeingUsedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerMain {
    private static final Map<User, Sender> users = new ConcurrentHashMap<>();
    private static BlockingQueue<Message> messages = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        final int port = 10101;
        new Thread(new MessageSenderRunnable(users, messages)).start();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
                try {
                    Socket client = serverSocket.accept();
                    acceptClient(client);
                    System.out.println("Client connected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    private static void acceptClient(Socket client) throws IOException {
        System.out.println("Accepting client");
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            User user = (User) in.readObject();
            System.out.println("SERVER: User = " + user.getUsername());
            boolean isValidUser;
            try{
                addUser(user, out, in);
                isValidUser = true;
            } catch (UsernameBeingUsedException _) {
                isValidUser = false;
            }
            System.out.println("SERVER: isValidUser = " + isValidUser);
            users.get(user).sendLoginStatus(isValidUser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void addUser(User user, ObjectOutputStream out, ObjectInputStream in) throws UsernameBeingUsedException {
        if(users.containsKey(user)) {
            throw new UsernameBeingUsedException();
        }
        users.put(user, new Sender(out));
        System.out.println("SERVER: User added "+user.getUsername());
        new Thread(new ReceivingRunnable(user, users, messages, in)).start();
    }

    public static void removeUser(User user) {
        users.remove(user);
    }
}
