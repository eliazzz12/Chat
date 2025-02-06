package com.dam.elias.chat.client.api.model;

import java.util.List;

public class GroupChat extends Chat {
    private List<User> users;

    public GroupChat(String name, List<User> users) {
        super(name);
        this.users = users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
}
