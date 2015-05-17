/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import Entity.Messages;
import Entity.Users;
import java.io.IOException;
import java.io.StringReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import session.MessageManager;
import session.UsersFacade;

/**
 *
 * @author Sergey
 */

@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    private static final Map<String, Session> sessions = new HashMap<>();
    
    @EJB
    UsersFacade usersFacade;
    @EJB
    MessageManager messageManager; 
    
    @OnOpen
    public void eventOpen(Session session){
        System.out.println("Соединение установленно...");
        String userLogin = session.getUserPrincipal().getName();
        sessions.put(userLogin, session);
    }
    
    @OnClose
    public void eventClose(Session session){
        System.out.println("Соединение закрыто");
        sessions.remove(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        System.err.println("Error WebSocket");
    } 


    @OnMessage
    public String handleMessage(String message, Session session) {
        String msgd = message;
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                
                String userLogin = jsonMessage.getString("from");
                String friendLogin = jsonMessage.getString("to");
                String msg = jsonMessage.getString("text");
                addMessage(userLogin, friendLogin, msg);
            }
        }
        
        System.out.println("Получено сообщение от клиента:"+message);
        String reply="Ваше сообщение: "+message+" было полученно;";
        return reply;
    }
    
    private JsonObject createAddMessage(Messages message) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("from", message.getFromUsersLogin().getLogin())
                .add("to", message.getToUsersLogin().getLogin())
                .add("text", message.getText())
                .add("date", message.getDate().toString())
                .build();
        return addMessage;
    }

    private void sendToFriendConnectedSessions(JsonObject message, String friendLogin) {
        if(sessions.containsKey(friendLogin)) {
            Session session = sessions.get(friendLogin);
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
    
    private void addMessage(String userLogin, String friendLogin, String text) {
        Users user = usersFacade.find(userLogin);
        Users friend = usersFacade.find(friendLogin);
        Messages message=messageManager.newMessage(friend, user, text);
        messageManager.addMessage(message);
 
        JsonObject addMessage = createAddMessage(message);
        sendToFriendConnectedSessions(addMessage, userLogin);
        sendToFriendConnectedSessions(addMessage, friendLogin);
    }
}
