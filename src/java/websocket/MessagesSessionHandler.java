/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;


/**
 *
 * @author Sergey
 */
public class MessagesSessionHandler {
 
    private final Set<Session> sessions = new HashSet<>();
    
     public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
}
