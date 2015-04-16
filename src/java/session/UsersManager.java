/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Groupuser;
import Entity.Users;
import java.util.List;
import java.util.Map;
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
public class UsersManager {
    @PersistenceContext(unitName = "MessangerPU")
    private EntityManager em;
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Integer addUser(final String login, final String password, final String passwordTwo) {
        try{
            if (login!=null && password!=null && passwordTwo!=null && password.equals(passwordTwo)){
                List resultList = em.createNamedQuery("Users.findByLogin").setParameter("login", login).getResultList();
                if (resultList.size()==0){
                    Users user=newUser(login, password);
                    return 0;
                } else {return 3;}
            }else {return 2;}
        }catch (Exception e){
            context.setRollbackOnly();
            e.printStackTrace();
            return 1;
        }
    }
    
    private Users newUser(String login, String password) {
        Users user = new Users(login, password);
        Groupuser privateGroup = new Groupuser("private", login);
        user.getGroupuserCollection().add(privateGroup);
        em.persist(user);
        return user;
    }
}
