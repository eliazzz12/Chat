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
    private static AppController controller;
    //TODO aceptar distintas ip por parámetro (--ip)
    private static String ip_address="sectamermelada.ddns.net";
    private static InetAddress address;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 340);
        stage.setTitle("Chat TCP Elias");
        stage.setScene(scene);
        stage.show();
        controller = fxmlLoader.getController();
    }


    public static void main(String[] args) {
        setup(args);
        launch();
        final int PORT = 10101;
        ChatManagerGUI cm = new ChatManagerGUI(address, PORT, controller);
        try {
            cm.connect();
        } catch (IOException e) {
            controller.noConnection(e);
        }
        Thread thread = new Thread(cm);
        thread.start();
    }

    private static void setup(String[] args) {
        try{
            if(args[0].equals("-l") || args[0].equals("--localhost")) {
                address = InetAddress.getLoopbackAddress();
            }
        } catch (IndexOutOfBoundsException e){
            try {
                address = InetAddress.getByName(ip_address);
            } catch (UnknownHostException ex) {
                controller.noConnection(ex);
            }
        }
    }
}