/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Owner
 */
@Stateless
public class IslandBean implements IslandBeanRemote {

    @Override
    public int add() {
        return 5;
    }

    @Override
    public List<Integer> getManufacturingItems(int mfgId) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        /*
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
            Statement sta = (Statement) con.createStatement();
            String query = "SELECT item FROM mfg_supply_shop WHERE mfg = " + mfgId;

            ResultSet rs = sta.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getInt("item"));
            }
            sta.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        */
        
        //Dummy Items
        list.add(1);
        list.add(2);
        list.add(3);

        return list;
    }

    //period in months
    @Override
    public List<Integer> getSalesHistory(int matNo, int period) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
            Statement sta = (Statement) con.createStatement();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dateStr = dateFormat.format(date);
            int currMonth = Integer.valueOf(dateStr.substring(5, 7));
            int currYear = Integer.valueOf(dateStr.substring(0, 4));
            System.out.println(currMonth + "");
            for (int i = 1; i <= period; i++) {
                int reqYear = currYear;
                int reqMonth = currMonth - i;
                if (reqMonth <= 0) {
                    reqMonth = 12 + reqMonth;
                    reqYear--;
                }
                String query = "SELECT * FROM saleshist WHERE item = " + matNo
                        + " AND sales_month = '" + reqMonth + "'";
                ResultSet rs = sta.executeQuery(query);
                while (rs.next()) {
                    System.out.println("get month: " + reqMonth + ": " + rs.getInt("amount_sold"));
                    list.add(rs.getInt("amount_sold"));
                }
            }
            sta.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return list;
    }

    @Override
    public int calculateSalesForecast(int matNo) {
        return 100;
    }

    @Override
    public String getMaterialDescription(int matno) {
        String desc = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
            Statement sta = (Statement) con.createStatement();
            String query = "SELECT description FROM material WHERE item_id = " + matno;

            ResultSet rs = sta.executeQuery(query);
            while (rs.next()) {
                desc = rs.getString("description");
            }
            sta.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return desc;
    }

    @Override
    public int getInventory(int matNo) {
        return 10;
    }
}
