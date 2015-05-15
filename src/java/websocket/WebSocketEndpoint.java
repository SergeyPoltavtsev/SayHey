/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import javax.ejb.EJB;
import javax.inject.Inject;
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
//    @Inject
//    private MessagesSessionHandler sessionHandler;

    @EJB
    UsersFacade usersFacade;
    @EJB
    MessageManager messageManager; 
    
    @OnOpen
    public void eventOpen(Session session){
        System.out.println("Соединение установленно...");
    }
    
    @OnClose
    public void eventClose(Session session){
        System.out.println("Соединение закрыто");
    }
    
    @OnMessage
    public String EventMessage(String message, Session session){
        System.out.println("Получено сообщение от клиента:"+message);
        String reply=message;
        return reply;
    }
    
    @OnError
    public void onError(Throwable error) {
        System.err.println("Error WebSocket");
    } 
    
//    @OnOpen
//    public void open(Session session) {
//        sessionHandler.addSession(session);
//    }
//
//    @OnClose
//    public void close(Session session) {
//        sessionHandler.removeSession(session);
//    }
//
//    @OnMessage
//    public String handleMessage(String message) {
//        String msgd = message;
//        try (JsonReader reader = Json.createReader(new StringReader(message))) {
//            JsonObject jsonMessage = reader.readObject();
//
//            if ("add".equals(jsonMessage.getString("action"))) {
//                
//                String userLogin = jsonMessage.getString("from");
//                String friendLogin = jsonMessage.getString("to");
//                String msg = jsonMessage.getString("message");
//                sessionHandler.addMessage(userLogin, friendLogin, msg);
//            }
//        }
//        
//        System.out.println("Получено сообщение от клиента:"+message);
//        String reply="Ваше сообщение: "+message+" было полученно;";
//        return reply;
//    }
//    
   
}
