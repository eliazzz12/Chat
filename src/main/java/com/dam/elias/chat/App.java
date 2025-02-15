package com.dam.elias.chat;

import com.dam.elias.chat.client.api.connection.Connection;
import com.dam.elias.chat.client.gui.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Connection connection;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 350);
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.setMaxWidth(scene.getWidth());
        stage.setMaxHeight(scene.getHeight());
        stage.setScene(scene);
        LoginController controller;
        controller = fxmlLoader.getController();
        controller.setStage(stage);
        connection = new Connection(getParameters());
        controller.setConnection(connection);
        stage.setTitle("Chat TCP DAM");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(connection.isConnected()) {
            connection.disconnect();
        }
    }
}