package com.dam.elias.chat.client.api.connection;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SendMessage implements Runnable {
    private ObjectOutputStream out;
    private Message message;

    public SendMessage(ObjectOutputStream out, Message message) {
        setOut(out);
        setMessage(message);
    }

    @Override
    public void run() {
        int tryCount = 0;
        boolean sent = false;
        do{
            System.out.println("SendMessage: enviando mensaje "+message.getText());
            try {
                out.writeObject(message);
                out.flush();
                message.sent();
                sent = true;
            } catch (IOException _) {
                tryCount++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException _) {
                    Thread.currentThread().interrupt();
                }
            }
        } while (!sent && tryCount < 1000);

//        if(sent){
//            chatManagerGUI.messageSent(message, chat);
//        } else {
//            chatManagerGUI.messageNotSent(message, chat);
//        }
    }

    public void setOut(ObjectOutputStream out) {
        if(out == null) {
            throw new IllegalArgumentException("ObjectOutputStream cannot be null");
        }
        this.out = out;
    }

    public void setMessage(Message message) {
        if(message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.message = message;
    }
}
