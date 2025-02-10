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
    private Stage stage;
    @FXML
    private TextField usernameInput;
    @FXML
    private Button loginButton;
    @FXML
    private Label availableLabel;

    private void launchApp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        MainController controller = fxmlLoader.getController();
        controller.setUser(user);
        controller.setConnection(connection);
        controller.setup();
        new Thread(new ReceiverClient(controller, connection.getIn())).start();
        stage.setScene(scene);
    }

    public void attemptLogin() throws IOException {
        boolean success = false;
        try {
            String username = this.usernameInput.getText();
            success = login(username);
        } catch (IOException e) {
            availableLabel.setText("Login not available at this time");
        }
        if(success){
            launchApp();
        } else {
            availableLabel.setText(userNameNotAvailableText);
        }
    }

    public boolean login(String username) throws IOException {
        System.out.println("Iniciando sesión..."+ username);
        setUser(createUser(username));
        connection.getOut().writeObject(user);
        System.out.println("esperando confirmación");
        boolean result = connection.getIn().readBoolean();
        System.out.println("Es usuario válido= "+result);
        return result;
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

    public void setStage(Stage stage) {
        if(stage == null){
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    public void setConnection(Connection connection) {
        if(connection == null){
            throw new IllegalArgumentException("Connection cannot be null");
        }
        System.out.println("Connection en LoginController ok");
        this.connection = connection;
    }
}
