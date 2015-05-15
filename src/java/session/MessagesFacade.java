/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Messages;
import Entity.Users;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sergey
 */
@Stateless
public class MessagesFacade extends AbstractFacade<Messages> {
    @PersistenceContext(unitName = "MessangerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagesFacade() {
        super(Messages.class);
    }
    
    public List<Messages> findMessageHistory(String fromUserLogin, String toUserLogin) {
        Query query = em.createNamedQuery("Messages.findMessageHistory").setParameter("from", fromUserLogin).setParameter("to", toUserLogin);
        query.setMaxResults(10);        
        return (List<Messages>) query.getResultList();
    }
    
    public List<Users> findAllChatsForUser(String userLogin) {
        Query query = em.createNamedQuery("Messages.findAllChatsForUser").setParameter("userLogin", userLogin);       
        
        List<Messages> result = query.getResultList();
        List<Users> peopleInConversation = new LinkedList<>();
        for(Messages msg : result) {
            Users personInConversation;
            if (msg.getFromUsersLogin().getLogin().equals(userLogin)) {
                personInConversation = msg.getToUsersLogin();
            } else {
                personInConversation = msg.getFromUsersLogin();
            }
            
            if(!peopleInConversation.contains(personInConversation)){
                peopleInConversation.add(personInConversation);
            }
        }
        
        return peopleInConversation;
    }
}
