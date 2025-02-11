package com.dam.elias.chat.client.gui.mediator;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.GroupChat;
import com.dam.elias.chat.client.api.model.User;

import java.util.List;

public interface OnlineUsersMediator {
    void newPrivateChat(User user);
    void newGroupChat(String name, List<User> users);
    void newGroupChat(GroupChat chat);
    void askForOnlineUsers();
    void setChatView();
}
