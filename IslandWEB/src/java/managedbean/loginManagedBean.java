/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbean;

import javax.ejb.EJB;
import ejb.FMSBeanRemote;
import javax.faces.event.ActionEvent;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author nataliegoh
 */
@Named(value = "loginManagedBean")
@Dependent
public class loginManagedBean {

    @EJB
    private FMSBeanRemote fmsbean;
    
    /**
     * Creates a new instance of loginManagedBean
     */
    public loginManagedBean() {
    }
    
    private String username;
    private String password;

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
    

    
    public String login (ActionEvent event) {
        int result;
        username = getUsername();
        password = getPassword();
        password = fmsbean.encryptPassword(username, password);
        
        try {
            result = fmsbean.verifyUser(username, password);
            if (result == 1) {
                return "index.xhtml?faces-redirect=true";
            }
        }
            
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "login.xhtml?faces-redirect=true";
    }
}
