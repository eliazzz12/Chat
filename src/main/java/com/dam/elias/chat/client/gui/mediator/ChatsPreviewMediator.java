package com.dam.elias.chat.client.gui.mediator;

import com.dam.elias.chat.client.api.model.Chat;

import java.util.List;

public interface ChatsPreviewMediator {
    void newChat(Chat chat);

    /**
     * Searches chat names.
     * @param text the search text
     * @return a list with Chats containing the search in their name
     */
    List<Chat> getChatsMatching(String text);
}
