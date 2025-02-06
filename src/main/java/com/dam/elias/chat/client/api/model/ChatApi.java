package com.dam.elias.chat.client.api.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChatApi {
    /**
     * Sends a message to the server
     * @param chatName the name of chat to which the message is directed
     * @param text the message to sendMessage
     */
    void send(String chatName, String text);

    /**
     * Receives a message from the server
     */
    void receive(Message message);

    boolean doesUserExist(String username) throws IOException;

    /**
     * Creates a new PrivateChat. The other user iniciated this chat
     * @param privateChat the new PrivateChat
     */
    void newPrivateChat(PrivateChat privateChat);

    /**
     * Tells to the AppController that a new PrivateChat has been created
     * @param user the user the new PrivateChat is with
     * @return a new PrivateChat
     */
    PrivateChat newPrivateChat(User user);

    /**
     * Tells to the AppController that a new GroupChat has been created
     * @param users the list of users included in this group chat
     * @return a new GroupChat
     */
    GroupChat newGroupChat(List<User> users);

    /**
     * Adds a new GroupChat this user has been added to
     * @param groupChat the new GroupChat
     */
    void newGroupChat(GroupChat groupChat);


    /**
     * Gets the last message from a chat
     * @param chat the chat to retrieve from
     * @return the last message sent in the chat
     */
    Message getLastMessage(Chat chat);

    /**
     * Checks whether a message has been sent to the server
     * @param message the message to check
     * @param chat the chat this message was sent to
     * @return true if the message was sent to the server, false otherwise
     */
    public boolean messageSentStatus(Message message, Chat chat);

    /**
     * Checks whether a message has been received by the other client
     * @param message the message to check
     * @param chat the chat this message was sent to
     * @return true if the message was received by the other client, false otherwise
     */
    public boolean messageReceivedStatus(Message message, PrivateChat chat);

    /**
     * Checks whether a message has been received by the other clients
     * @param message the message to check
     * @param chat the chat this message was sent to
     * @return a Map with the group users' and true if the message was received by the client, false otherwise
     */
    public Map<User, Boolean> messageReceivedStatus(Message message, GroupChat chat);
}