/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author nataliegoh
 */
@Entity(name="Staff")
public class StaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String username;
    private String password;
    @OneToOne(cascade={CascadeType.PERSIST})
    private ContactEntity contact;
    @OneToMany(cascade={CascadeType.PERSIST})
    private Collection<RolesEntity> roles = new ArrayList<RolesEntity>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void create(String name, String username, String password){
        this.setId(id);
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public ContactEntity getContact() {
        return contact;
    }
    
    public void setContact(ContactEntity contact){
        this.contact = contact;
    }
    
    public Collection<RolesEntity> getRoles() {
        return roles;
    }
    
    public void setRoles(Collection<RolesEntity> roles) {
        this.roles = roles;
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
        if (!(object instanceof StaffEntity)) {
            return false;
        }
        StaffEntity other = (StaffEntity) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "ejb.StaffEntity[ id=" + id + " ]";
    }
    
}
