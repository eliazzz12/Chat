package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.client.api.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class UserInfoController {
    @FXML
    private Label username;
    @FXML
    private CheckBox checkbox;

    public void setUserInfo(User user) {
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        username.setText(user.getUsername());
    }

    public boolean isChecked() {
        return checkbox.isSelected();
    }
}
