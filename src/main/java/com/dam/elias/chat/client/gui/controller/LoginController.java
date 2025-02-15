package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.api.connection.ReceiverClient;
import com.dam.elias.chat.client.api.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private Connection connection;
    private User user;

    private static final String userNameNotAvailableText = "Username not available";
    @FXML
    private TextField usernameInput;
    @FXML
    private Label availableLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameInput.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    attemptLogin();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void launchApp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.setMaxHeight(scene.getHeight());
        stage.setMaxWidth(scene.getWidth());
        MainController controller = fxmlLoader.getController();
        controller.setUser(user);
        controller.setConnection(connection);
        controller.setup();
        new Thread(new ReceiverClient(controller, connection.getIn())).start();
        stage.setScene(scene);
    }

    public void attemptLogin() throws IOException {
        try {
            connection.connect();
            String username = this.usernameInput.getText();
            boolean user_available = login(username);
            if(user_available){
                launchApp();
            } else {
                availableLabel.setText(userNameNotAvailableText);
            }
        } catch (IOException | NullPointerException e) {
            availableLabel.setText("Login not available at this time");
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
