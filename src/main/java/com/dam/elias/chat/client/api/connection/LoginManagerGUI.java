package com.dam.elias.chat.client.api.connection;

import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.controller.LoginController;
import javafx.application.Application;

import java.io.IOException;

public class LoginManagerGUI extends Connection {
    private static LoginManagerGUI instance;
    private LoginController controller;
    private User user;

    private LoginManagerGUI(Application.Parameters param, LoginController controller) throws IOException {
        setup(param);
        setController(controller);
    }

    public static LoginManagerGUI getInstance(Application.Parameters param, LoginController controller) throws IOException {
        LoginManagerGUI result = instance;
        if (result != null) {
            return result;
        }
        synchronized(LoginManagerGUI.class) {
            if (instance == null) {
                instance = new LoginManagerGUI(param, controller);
            }
            return instance;
        }
    }

    public boolean login(String username) throws IOException {
        System.out.println("Iniciando sesión..."+ username);
        setUser(createUser(username));
        out.writeObject(user);
        System.out.println("esperando confirmación");
        boolean result = in.readBoolean();
        System.out.println("Es usuario válido= "+result);
//        return in.readBoolean();
        return result;
    }

    public void setController(LoginController controller) {
        if(controller == null){
            throw new IllegalArgumentException("Login controller cannot be null");
        }
        this.controller = controller;
    }

    public User createUser(String username){
        User newUser = new User(username);
        return newUser;
    }

    public void setUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
