package com.dam.elias.chat.server;

import com.dam.elias.chat.client.api.model.Chat;
import com.dam.elias.chat.client.api.model.GroupChat;
import com.dam.elias.chat.client.api.model.Message;
import com.dam.elias.chat.client.api.model.User;
import com.dam.elias.chat.server.exceptions.HandlerNotFoundException;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ReceivingRunnable implements Runnable {
    private User user;
    private static Map<User, Sender> users;
    private static BlockingQueue<Message> messageQueue;
    private ObjectInputStream in;

    public ReceivingRunnable(User user, Map<User, Sender> users, BlockingQueue<Message> messageQueue,
                             ObjectInputStream in) {
        setUser(user);
        setUsers(users);
        setMessageQueue(messageQueue);
        setIn(in);
    }

    @Override
    public void run() {
        boolean userIsOnline = true;
        while(userIsOnline) {
            try {
                handle(in.readObject());
            } catch (Exception _) {
                users.remove(user);
                userIsOnline = false;
            }
        }
    }

    private void setUsers(Map<User, Sender> users_) {
        if(users_ == null) {
            throw new IllegalArgumentException("users map cannot be null");
        }
        users = users_;
    }

    private void setMessageQueue(BlockingQueue<Message> messageQueue_) {
        if(messageQueue_ == null) {
            throw new IllegalArgumentException("Messages queue cannot be null");
        }
        messageQueue = messageQueue_;
    }

    private void setIn(ObjectInputStream in) {
        if(in == null) {
            throw new IllegalArgumentException("Object input stream cannot be null");
        }
        this.in = in;
    }

    private interface Handler {
        void handle(Object o);
    }

    private static final Map<Class,Handler> dispatch = Map.ofEntries(
        Map.entry(Message.class, o -> handleMessage((Message) o)),
        Map.entry(User.class, o -> handleUser((User) o)),
        Map.entry(Chat.class, o -> handleChat((Chat) o)),
        Map.entry(Object[].class, o -> handleObjectArray((Object[]) o)),
        Map.entry(ArrayList.class, o -> handleList((ArrayList<User>) o))
    );

    private static void handle(Object o) {
        Handler h = dispatch.get(o.getClass());
        if (h == null) {
            throw new HandlerNotFoundException("Handler not found for " + o.getClass()+ " class");
        }
        h.handle(o);
    }

    private static void handleList(List<User> o) {
        User askingUser = o.getFirst();
        List<User> list = new ArrayList<>();
        list.addAll(users.keySet());
        list.remove(askingUser);
        users.get(askingUser).sendUserList(list);
    }

    static void handleMessage(Message message){
        messageQueue.add(message);
    }

    static void handleUser(User userToUpdate){
        throw new UnsupportedOperationException("Not implemented yet");
        //TODO updateUser(): actualizar datos en todos los clientes que tengan conversación con el user
    }

    static void handleObjectArray(Object[] array){
        boolean exists = false;
        User askingUser = (User) array[0];
        Class searchedClass = (Class) array[1];
        String searchedName = (String) array[2];

        if(searchedClass == User.class) {
            exists = userExists(searchedName);
        } else if(searchedClass == GroupChat.class) {
            exists = groupChatExists(searchedName);
        }

        users.get(askingUser).sendUserStatus(exists);
    }

    private static boolean groupChatExists(String groupName){
        throw new UnsupportedOperationException("Not implemented yet");
        //TODO crear coleccion groupChats Set<GroupChat>
//        boolean exists = false;
//        Set<GroupChat> gcSet = groupChats.keySet();
//        for(GroupChat gc : gcSet){
//            if(gc.getName().equals(groupName)){
//                exists = true;
//                break;
//            }
//        }
//        return exists;
    }

    private static boolean userExists(String username) {
        boolean exists = false;
        Set<User> usersSet = users.keySet();
        for(User u : usersSet){
            if(u.getUsername().equals(username)){
                exists = true;
                break;
            }
        }
        return exists;
    }

    static void handleChat(Chat chat){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        this.user = user;
    }
}
