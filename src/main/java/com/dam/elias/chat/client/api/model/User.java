package com.dam.elias.chat.client.api.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User implements Serializable {
    /**
     * El nombre de usuario es él id del usuario,
     * queda libre para que lo usen otros usuarios cuando se cierra la sesión.
     */
    private String username;
    /**
     * Mapea cada chat con su id.
     */
    private Map<Integer, Chat> chats;
    /**
     * Imágen de perfil del usuario
     */
    private Image profilePicture;

    public User(String username) {
        setUsername(username);
        chats = new HashMap<>();
    }

    public void addChat(Chat chat) {
//        chats.put(chat.getId(), chat);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChats(Map<Integer, Chat> chats) {
        this.chats = chats;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public Map<Integer, Chat> getChats() {
        return chats;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
