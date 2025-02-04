package com.dam.elias.chat.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppController {

    public void noConnection(Exception e) {
        //TODO implementar que ocurre cuando no puede conectarse
        System.out.println("No connection: " + e.getMessage());
    }
}