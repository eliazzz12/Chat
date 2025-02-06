package com.dam.elias.chat;

import com.dam.elias.chat.client.api.connection.LoginManagerGUI;
import com.dam.elias.chat.client.gui.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 450);
        stage.setScene(scene);
        LoginController controller;
        try {
            controller = fxmlLoader.getController();
            LoginManagerGUI lm = LoginManagerGUI.getInstance(getParameters(), controller);
            controller.setLoginManager(lm);
            controller.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        stage.setTitle("Chat TCP DAM");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}