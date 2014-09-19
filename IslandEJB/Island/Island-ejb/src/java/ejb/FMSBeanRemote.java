/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;

/**
 *
 * @author nataliegoh
 */
@Remote
public interface FMSBeanRemote {
    public void createStaff(String name, String username, String password);
    
    public void createContact(String contact, String email);
    
    public void createRoles(String roles);
     
    public void addRoles();
    
    public void staffPersist();
    
    public int verifyUser(String username, String password);
    
    public ArrayList<String> getStaffDetails(String username);
    
    public String encryptPassword(String username, String password);

    public boolean staffExists(String email);

    public List<Vector> getAllAcounts();
}
