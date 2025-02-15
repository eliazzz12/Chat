module com.dam.elias.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.httpserver;
    requires java.naming;
    requires jdk.compiler;


    opens com.dam.elias.chat to javafx.fxml;
    exports com.dam.elias.chat;
    exports com.dam.elias.chat.client.gui.controller;
    opens com.dam.elias.chat.client.gui.controller to javafx.fxml;
    exports com.dam.elias.chat.client.api.model;
    opens com.dam.elias.chat.client.api.model to javafx.fxml;
    exports com.dam.elias.chat.client.gui;
    opens com.dam.elias.chat.client.gui to javafx.fxml;
    exports com.dam.elias.chat.client.gui.states;
    opens com.dam.elias.chat.client.gui.states to javafx.fxml;
}