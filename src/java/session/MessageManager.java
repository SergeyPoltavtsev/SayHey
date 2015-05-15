/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Messages;
import Entity.Users;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sergey
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageManager {

    @PersistenceContext(unitName = "MessangerPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean addMessage(Messages message) {
        try{
            em.persist(message);
        }catch (Exception e){
            context.setRollbackOnly();
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Messages newMessage(Users friend, Users user, String text) {
        Messages msg = new Messages(text);
        msg.setFromUsersLogin(user);
        msg.setToUsersLogin(friend);
        return msg;
    }
    
}
