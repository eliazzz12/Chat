package com.dam.elias.chat.server;

import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.exceptions.MessageNotSentException;
import com.dam.elias.chat.server.exceptions.StatusNotSentException;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sender {
    private ObjectOutputStream out;

    public Sender(ObjectOutputStream out) {
        setOut(out);
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            throw new MessageNotSentException(e);
        }
    }

    public void sendLoginStatus(boolean status) {
        try {
            out.writeBoolean(status);
            out.flush();
        } catch (IOException e) {
            throw new StatusNotSentException(e);
        }
    }

    public void sendUserStatus(boolean status) {
        try {
            out.writeBoolean(status);
            out.flush();
        } catch (IOException e) {
            throw new StatusNotSentException(e);
        }
    }

    public void setOut(ObjectOutputStream out) {
        if(out == null) {
            throw new IllegalArgumentException("Object output stream cannot be null");
        }
        this.out = out;
    }
}
