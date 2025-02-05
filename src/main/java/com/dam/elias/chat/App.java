package com.dam.elias.chat;

import com.dam.elias.chat.api.model.ChatManagerGUI;
import com.dam.elias.chat.gui.controllers.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 340);
        try {
            AppController controller = fxmlLoader.getController();
            ChatManagerGUI cm = ChatManagerGUI.getInstance(getParameters(), controller);
            controller.setChatManager(cm);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        stage.setTitle("Chat TCP Elias");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}