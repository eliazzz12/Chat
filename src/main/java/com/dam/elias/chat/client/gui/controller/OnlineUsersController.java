package com.dam.elias.chat.client.gui.controller;

import com.dam.elias.chat.App;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.client.gui.mediator.Mediator;
import com.dam.elias.chat.client.gui.mediator.OnlineUsersMediator;
import com.dam.elias.chat.client.gui.mediator.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineUsersController implements ViewController {
    OnlineUsersMediator mediator;
    Map<User, UserInfoController> userInfoControllers = new HashMap<>();

    @FXML
    private VBox vb_users;
    @FXML
    private TextField inputGroupName;

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
        vb_users.getChildren().addFirst(item);
    }

    public void newChat(MouseEvent mouseEvent) {
        List<User> users = getSelectedUsers();
        if(users.size() == 1) {
            mediator.newPrivateChat(users.getFirst());
        } else if(users.size() > 1) {
            if(inputGroupName.getText().isEmpty()) {
                inputGroupName.requestFocus();
            } else {
                String name = inputGroupName.getText();
                mediator.newGroupChat(name, users);
            }
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

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (OnlineUsersMediator) mediator;
    }
}
