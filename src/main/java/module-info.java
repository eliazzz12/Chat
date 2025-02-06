module com.dam.elias.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.httpserver;


    opens com.dam.elias.chat to javafx.fxml;
    exports com.dam.elias.chat;
    exports com.dam.elias.chat.client.gui.controllers;
    opens com.dam.elias.chat.client.gui.controllers to javafx.fxml;
}