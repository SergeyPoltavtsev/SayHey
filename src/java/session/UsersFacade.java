    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Users;
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
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "MessangerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findAllCustomersWithName(String userLogin, String userLoginPattern) {
        Query query = em.createNamedQuery("Users.findAllCustomersWithName").setParameter("profilelogin", userLogin).setParameter("userNamePattern", userLoginPattern+"%");       
        return (List<Users>) query.getResultList();
    }
    
}
