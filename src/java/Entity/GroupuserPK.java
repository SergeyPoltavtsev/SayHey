/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sergey
 */
@Embeddable
public class GroupuserPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "users_login")
    private String usersLogin;

    public GroupuserPK() {
    }

    public GroupuserPK(String name, String usersLogin) {
        this.name = name;
        this.usersLogin = usersLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsersLogin() {
        return usersLogin;
    }

    public void setUsersLogin(String usersLogin) {
        this.usersLogin = usersLogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        hash += (usersLogin != null ? usersLogin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupuserPK)) {
            return false;
        }
        GroupuserPK other = (GroupuserPK) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        if ((this.usersLogin == null && other.usersLogin != null) || (this.usersLogin != null && !this.usersLogin.equals(other.usersLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GroupuserPK[ name=" + name + ", usersLogin=" + usersLogin + " ]";
    }
    
}
