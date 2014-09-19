/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author nataliegoh
 */
@Remote
public interface FMSBeanRemote {
    public void createStaff(Long staffid, String name, String username, String password);
    
    public boolean staffExists(Long staffid);
    
    public void createContact(Long staffid, String contact, String email);
    
    public void createRoles(Long staffid, String roles);
     
    public void addRoles();
    
    public void staffPersist();
    
    public int verifyUser(String username, String password);
    
    public ArrayList<String> getStaffDetails(String username);
}
