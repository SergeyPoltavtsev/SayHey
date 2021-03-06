/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sergey
 */
@Entity
@Table(name = "messages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findById", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.findByText", query = "SELECT m FROM Messages m WHERE m.text = :text"),
    @NamedQuery(name = "Messages.findByDate", query = "SELECT m FROM Messages m WHERE m.date = :date"),
    @NamedQuery(name = "Messages.findMessageHistory", query = "SELECT m FROM Messages m WHERE (m.fromUsersLogin.login = :from and m.toUsersLogin.login = :to) OR (m.fromUsersLogin.login = :to and m.toUsersLogin.login = :from) ORDER BY m.date DESC"),
    @NamedQuery(name = "Messages.findAllChatsForUser", query = "SELECT DISTINCT m FROM Messages m WHERE m.toUsersLogin.login=:userLogin OR m.fromUsersLogin.login=:userLogin ORDER BY m.date DESC")
})
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "from_users_login", referencedColumnName = "login")
    @ManyToOne(optional = false)
    private Users fromUsersLogin;
    @JoinColumn(name = "to_users_login", referencedColumnName = "login")
    @ManyToOne(optional = false)
    private Users toUsersLogin;

    public Messages() {
    }

    public Messages(Integer id) {
        this.id = id;
    }

    public Messages(Integer id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public Messages(String text) {
        this.text = text;
        this.date = new Date();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getFromUsersLogin() {
        return fromUsersLogin;
    }

    public void setFromUsersLogin(Users fromUsersLogin) {
        this.fromUsersLogin = fromUsersLogin;
    }

    public Users getToUsersLogin() {
        return toUsersLogin;
    }

    public void setToUsersLogin(Users toUsersLogin) {
        this.toUsersLogin = toUsersLogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Messages[ id=" + id + " ]";
    }
    
}
