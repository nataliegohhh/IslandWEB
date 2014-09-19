/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nataliegoh
 */
@Stateful

public class FMSBean implements FMSBeanRemote{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    private StaffEntity staffEntity;
    private ContactEntity contactEntity;
    private RolesEntity rolesEntity;
    private Collection<RolesEntity> roles;
    
    public FMSBean() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FMSBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void createStaff(String name, String username, String password) {
        staffEntity = new StaffEntity();
        String encrypted = encryptPassword(username, password);
        staffEntity.create(name, username, encrypted);
        roles = new ArrayList<RolesEntity>();
    }
    
    @Override
    public boolean staffExists(String email) {
            Query q;
            q = em.createQuery("SELECT TRUE FROM Contact c where c.email= :param");
            q.setParameter("param", email);
        return !q.getResultList().isEmpty();
    }
    
    @Override
    public void createContact(String contact, String email) {
        contactEntity = new ContactEntity();
        contactEntity.create(contact, email);
    }
    
    @Override
    public void createRoles(String roles) {
        rolesEntity = new RolesEntity();
        rolesEntity.create(roles);
    }
    
    @Override
    public void addRoles() {
        roles.add(rolesEntity);
    }
            
    @Override
    public void staffPersist() {
        staffEntity.setContact(contactEntity);
        staffEntity.setRoles(roles);
        em.persist(staffEntity);
        em.flush();
    }
    
    @Override
    public List<Vector> getAllAcounts() {
        Query q = em.createQuery("SELECT s from Staff s");
        List<Vector> staffList = new ArrayList();
        for (Object o : q.getResultList()) {
            StaffEntity s = (StaffEntity) o;
            Vector staff = new Vector();
            staff.add(s.getId());
            staff.add(s.getName());
            staff.add(s.getUsername());
            staff.add(s.getRoles());
            staffList.add(staff);
        }
        return staffList;
    }
    
    @Override
    public String encryptPassword(String username, String password) {
        String encrypted = null;
        if (password == null)
            return null;
        else {
            try {
                password = password.concat(username);
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes(), 0 , password.length());
                encrypted = new BigInteger(1, md.digest()).toString(16);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return encrypted;
    }
    
    @Override
    public int verifyUser(String username, String password) { 
        try {
        password = encryptPassword(username, password);
        Query q;
        q = em.createQuery("SELECT s.password FROM Staff s where s.username= :param");
        q.setParameter("param", username);
            String pwd = (String)q.getSingleResult();
            System.out.println("Password entered = " + password);
            System.out.println("Password found = " + pwd);
            if (pwd == null) {
                System.out.println("PASSWORD NULL");
                return 0;
            }
            else {
                System.out.println("SURVIVED");
                if (pwd.equals(password))
                    return 1;
                else
                    return -1;
            }
             
        } catch (NoResultException ec) {
            System.out.println("ERROR");
            return 0;
        }
    }
    
    @Override
    public ArrayList<String> getStaffDetails(String username) {
        ArrayList<String> details = new ArrayList<String>();
        Query q = em.createQuery("SELECT s FROM Staff s where s.username= :param");
        q.setParameter("param", username);
            Long id = (Long)q.getSingleResult();
        staffEntity = em.find(StaffEntity.class, id);
        details.add(staffEntity.getName());
        details.add(staffEntity.getContact().getEmail());
        details.add(staffEntity.getContact().getContact());
        return details;
    }
    
}
