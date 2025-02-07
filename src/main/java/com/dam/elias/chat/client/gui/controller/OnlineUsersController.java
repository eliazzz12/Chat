package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.GuiComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineUsersController extends GuiComponent {
    Map<User, UserInfoController> userInfoControllers = new HashMap<>();
    @FXML
    private VBox vb_users;

    public void setUsers(List<User> users) throws IOException {
        if(users == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        drawUsers(users);
    }

    private void drawUsers(List<User> users) throws IOException {
        for(User user : users) {
            drawUser(user);
        }
    }

    private void drawUser(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("user-info-preview.fxml"));
        Parent item = fxmlLoader.load();
        UserInfoController controller = fxmlLoader.getController();
        controller.setUserInfo(user);
        userInfoControllers.put(user, controller);
        vb_users.getChildren().add(0, item);
    }

    public void nuevoChat(MouseEvent mouseEvent) {
        List<User> users = getSelectedUsers();
        if(users.size() == 1) {
            cm.newPrivateChat(users.getFirst());
        } else if(users.size() > 1) {
            cm.newGroupChat(users);
        }
    }

    private List<User> getSelectedUsers() {
        List<User> list = new ArrayList<>();
        userInfoControllers.forEach((user, controller) -> {
            if(controller.isChecked()){
                list.add(user);
            }
        });
        return list;
    }
}
