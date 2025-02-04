package com.dam.elias.chat.api.model;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class User {
    /**
     * El nombre de usuario es él Id del usuario,
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
}
