package com.dam.elias.chat.server;

import com.dam.elias.chat.client.api.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageSenderRunnable implements Runnable {
    private Map<User, Sender> users;
    private BlockingQueue<Message> queue;

    public MessageSenderRunnable(Map<User, Sender> users, BlockingQueue<Message> queue) {
        setUsers(users);
        setQueue(queue);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message message = queue.take();
                Chat chat = message.getChat();
                if(chat.isPrivate()) {
                    PrivateChat privateChat = (PrivateChat) chat;
                    User receiver = privateChat.getOtherUser();
                    users.get(receiver).sendMessage(message);
                } else {
                    GroupChat groupChat = (GroupChat) chat;
                    List<User> userList = groupChat.getUsers();
                    userList.forEach(receiver -> users.get(receiver).sendMessage(message));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void setUsers(Map<User, Sender> users) {
        if(users == null) {
            throw new IllegalArgumentException("users map cannot be null");
        }
        this.users = users;
    }

    private void setQueue(BlockingQueue<Message> queue) {
        if(queue == null) {
            throw new IllegalArgumentException("Messages queue cannot be null");
        }
        this.queue = queue;
    }
}
