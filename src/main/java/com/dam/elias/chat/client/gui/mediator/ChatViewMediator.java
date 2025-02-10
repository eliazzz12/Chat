package com.dam.elias.chat.client.gui.mediator;

public interface ChatViewMediator {
    /**
     * Sends a new message to a chat
     * @param chatName the name of the chat
     * @param text the text to send
     */
    void sendMessage(String chatName, String text);
}
