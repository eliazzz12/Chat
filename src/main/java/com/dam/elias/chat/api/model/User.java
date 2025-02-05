package com.dam.elias.chat.api.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    /**
     * El nombre de usuario es él id del usuario,
     * queda libre para que lo usen otros usuarios cuando se cierra la sesión.
     */
    private String username;
    /**
     * Mapea cada chat con su id.
     */
    private Map<Integer, Chat> chats = new HashMap<>();
    /**
     * Imágen de perfil del usuario
     */
    private Image profilePicture;

    public User(String username) {
        setUsername(username);
    }

    public void addChat(Chat chat) {
        chats.put(chat.getId(), chat);
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
}
