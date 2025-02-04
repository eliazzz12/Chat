module com.dam.elias.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.dam.elias.chat to javafx.fxml;
    exports com.dam.elias.chat;
    exports com.dam.elias.chat.gui.controllers;
    opens com.dam.elias.chat.gui.controllers to javafx.fxml;
}