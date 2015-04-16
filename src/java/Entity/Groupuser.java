/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sergey
 */
@Entity
@Table(name = "groupuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupuser.findAll", query = "SELECT g FROM Groupuser g"),
    @NamedQuery(name = "Groupuser.findByName", query = "SELECT g FROM Groupuser g WHERE g.groupuserPK.name = :name"),
    @NamedQuery(name = "Groupuser.findByUsersLogin", query = "SELECT g FROM Groupuser g WHERE g.groupuserPK.usersLogin = :usersLogin")})
public class Groupuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupuserPK groupuserPK;
    @JoinColumn(name = "users_login", referencedColumnName = "login", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Groupuser() {
    }

    public Groupuser(GroupuserPK groupuserPK) {
        this.groupuserPK = groupuserPK;
    }

    public Groupuser(String name, String usersLogin) {
        this.groupuserPK = new GroupuserPK(name, usersLogin);
    }

    public GroupuserPK getGroupuserPK() {
        return groupuserPK;
    }

    public void setGroupuserPK(GroupuserPK groupuserPK) {
        this.groupuserPK = groupuserPK;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupuserPK != null ? groupuserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupuser)) {
            return false;
        }
        Groupuser other = (Groupuser) object;
        if ((this.groupuserPK == null && other.groupuserPK != null) || (this.groupuserPK != null && !this.groupuserPK.equals(other.groupuserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Groupuser[ groupuserPK=" + groupuserPK + " ]";
    }
    
}
