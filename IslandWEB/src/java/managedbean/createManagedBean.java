/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbean;

import ejb.FMSBeanRemote;
import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author nataliegoh
 */
@Named(value = "createManagedBean")
@Dependent  
public class createManagedBean {

    @EJB
    private FMSBeanRemote fmsbean;
    private String name;
    private String email;
    private String contact;
    private String username;
    private String password;
    private String roles;

    /**
     * Creates a new instance of createManagedBean
     */
    public createManagedBean() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    //String StaffID, String name, String email, String contact, String username, String password, String roles
    public void create(ActionEvent event) throws IOException {
        name = getName();
        email = getEmail();
        contact = getContact();
        username = getUsername();
        password = getPassword();
        roles = getRoles();
        
        password = fmsbean.encryptPassword(username, password);
        boolean exists = fmsbean.staffExists(email);
        if (exists) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/createNewUser.xhtml");
        }
        else {
            fmsbean.createStaff(name, username, password);
            fmsbean.createContact(contact, email);
            fmsbean.createRoles(roles);
            fmsbean.staffPersist();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        }
    }
    
    
    
}
