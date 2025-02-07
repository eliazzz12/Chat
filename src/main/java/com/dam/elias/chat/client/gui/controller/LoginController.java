package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.connection.LoginManagerGUI;
import com.dam.elias.chat.client.api.model.ChatManagerGUI;
import com.dam.elias.chat.client.api.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private LoginManagerGUI loginManager;
    private Stage stage;
    @FXML
    private TextField usernameInput;
    @FXML
    private Button loginButton;
    @FXML
    private Label availableLabel;
    private static final String userNameNotAvailableText = "Username not available";
    private boolean connected;
    private User user;

    private void launchApp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        try {
            AppController controller = fxmlLoader.getController();
            ChatManagerGUI cm = ChatManagerGUI.getInstance(loginManager.getParameters(), controller, user);
            controller.setChatManagerGUI(cm);
            controller.initialize(usernameInput.getText());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
//        stage.show();
    }

    public void attemptLogin() throws IOException {
        boolean success = false;
        try {
            if(!connected){
                loginManager.connect();
            }
            String username = this.usernameInput.getText();
            success = loginManager.login(username);
            user = loginManager.getUser();
        } catch (IOException e) {
            availableLabel.setText("Login not available at this time");
        }
        if(success){
            System.out.println("Lanzando app");
            launchApp();
        } else {
            availableLabel.setText(userNameNotAvailableText);
        }
    }

    public void setLoginManager(LoginManagerGUI loginManager) {
        if(loginManager == null){
            throw new IllegalArgumentException("Login manager cannot be null");
        }
        this.loginManager = loginManager;
    }

    public void setup() {
        stage = (Stage) usernameInput.getParent().getScene().getWindow();
    }
}
