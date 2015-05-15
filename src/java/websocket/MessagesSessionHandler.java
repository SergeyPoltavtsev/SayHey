/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import Entity.Messages;
import Entity.Users;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import session.MessageManager;
import session.UsersFacade;

/**
 *
 * @author Sergey
 */
public class MessagesSessionHandler {
//    @EJB
//    UsersFacade usersFacade;
//    @EJB
//    MessageManager messageManager; 
    private final Set<Session> sessions = new HashSet<>();
    
     public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void addMessage(String userLogin, String friendLogin, String text) {
//        Users user = usersFacade.find(userLogin);
//        Users friend = usersFacade.find(friendLogin);
//        Messages message=messageManager.newMessage(friend, user, text);
//        messageManager.addMessage(message);
//        
//        JsonObject addMessage = createAddMessage(message);
//        sendToAllConnectedSessions(addMessage);
    }
    
    private JsonObject createAddMessage(Messages message) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("fromUserLogin", message.getFromUsersLogin().getLogin())
                .add("toUserLogin", message.getFromUsersLogin().getLogin())
                .add("text", message.getText())
                .add("date", message.getDate().toString())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
        }
    }
}
