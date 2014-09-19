/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package islandclient;

import ejb.IslandBeanRemote;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Owner
 */
public class Main {

    @EJB
    private static IslandBeanRemote islandBean;

    public static void main(String[] args) {
        Main client = new Main();

        String choice = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!choice.equals("0")) {
            System.out.println("Select an option");
            System.out.println("a: Initialise");
            System.out.println("b: Add Dummy Sales History");

            try {
                choice = br.readLine();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            if (choice.equals("a")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
                    Statement sta = (Statement) con.createStatement();

                    String reset1 = "DROP TABLE IF EXISTS user";
                    String reset2 = "DROP TABLE IF EXISTS saleshist";
                    String reset3 = "DROP TABLE IF EXISTS material";
                    String reset4 = "DROP TABLE IF EXISTS component";
                    String reset5 = "DROP TABLE IF EXISTS sell";
                    String reset6 = "DROP TABLE IF EXISTS category";
                    String reset7 = "DROP TABLE IF EXISTS store";

                    String create1 = "CREATE TABLE user ("
                            + "name VARCHAR(32) PRIMARY KEY,"
                            + "password VARCHAR(32) NOT NULL"
                            + ");";

                    String create2 = "CREATE TABLE saleshist ("
                            + "item BIGINT,"
                            + "sales_year INT,"
                            + "sales_month INT,"
                            + "amount_sold INT,"
                            + "PRIMARY KEY(item, sales_year, sales_month)"
                            + ");";

                    String create3 = "CREATE TABLE material ("
                            + "item_id BIGINT PRIMARY KEY,"
                            + "name VARCHAR(64),"
                            + "description VARCHAR(64),"
                            + "category BIGINT"
                            //+ "FOREIGN KEY (category) REFERENCES category(category_id)"
                            + ");";

                    String create4 = "CREATE TABLE component ("
                            + "item BIGINT,"
                            + "consist_of BIGINT,"
                            + "number_of INT,"
                            + "PRIMARY KEY(item,consist_of)"
                            + ");";

                    String create5 = "CREATE TABLE store ("
                            + "store_id BIGINT PRIMARY KEY,"
                            + "name VARCHAR(64)"
                            + ");";

                    String create6 = "CREATE TABLE sell ("
                            + "store BIGINT,"
                            + "item BIGINT,"
                            + "PRIMARY KEY (store,item)"
                            + ");";

                    String create7 = "CREATE TABLE category ("
                            + "category_id BIGINT PRIMARY KEY,"
                            + "name VARCHAR(64)"
                            + ");";

                    sta.executeUpdate(reset1);
                    sta.executeUpdate(reset2);
                    sta.executeUpdate(reset3);
                    sta.executeUpdate(reset4);
                    sta.executeUpdate(reset5);
                    sta.executeUpdate(reset6);
                    sta.executeUpdate(reset7);
                    sta.executeUpdate(create1);

                    System.out.println("create7");
                    sta.executeUpdate(create7);
                    System.out.println("create1");
                    sta.executeUpdate(create2);
                    System.out.println("create2");
                    sta.executeUpdate(create3);
                    System.out.println("create3");
                    sta.executeUpdate(create4);
                    System.out.println("create4");
                    sta.executeUpdate(create5);
                    System.out.println("create5");
                    sta.executeUpdate(create6);
                    System.out.println("create6");

                    /*
                     sta.addBatch(reset1);
                     sta.addBatch(reset2);
                     sta.addBatch(reset3);
                     sta.addBatch(reset4);
                     sta.addBatch(create1);
                     sta.addBatch(create2);
                     sta.addBatch(create3);
                     sta.addBatch(create4);
                     sta.addBatch(create5);
                     sta.addBatch(create6);
                     sta.addBatch(create7);
                     sta.executeBatch();
                     */
                    // Insert Category
                    ArrayList<String> cats = new ArrayList<String>();
                    String filename = "C:\\Users\\pern\\Desktop\\category.txt";
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            cats.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < cats.size(); i++) {
                        String insertCat = "INSERT INTO category VALUES ("
                                + (i + 1)
                                + ",'"
                                + cats.get(i)
                                + "')";
                        System.out.println(insertCat);
                        sta.executeUpdate(insertCat);
                    }

                    //Insert Goods
                    ArrayList<String> goods = new ArrayList<String>();
                    filename = "C:\\Users\\pern\\Desktop\\goods.txt";
                    reader = new BufferedReader(new FileReader(filename));
                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            for (int i = 0; i < 3; i++) {
                                goods.add(parts[i]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int mat = 0; mat < goods.size() / 3; mat++) {
                        String insertMat = "INSERT INTO material VALUES ("
                                + (mat + 1)
                                + ",'"
                                + goods.get(mat * 3)
                                + "','"
                                + goods.get(mat * 3 + 1)
                                + "',"
                                + goods.get(mat * 3 + 2)
                                + ")";
                        System.out.println(insertMat);
                        sta.executeUpdate(insertMat);
                    }

                    //Insert Component
                    ArrayList<String> comps = new ArrayList<String>();
                    filename = "C:\\Users\\pern\\Desktop\\component.txt";
                    reader = new BufferedReader(new FileReader(filename));
                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            for (int i = 0; i < 3; i++) {
                                comps.add(parts[i]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < comps.size() / 3; i++) {
                        String insertComp = "INSERT INTO component VALUES ("
                                + comps.get(i * 3)
                                + ","
                                + comps.get(i * 3 + 1)
                                + ","
                                + comps.get(i * 3 + 2)
                                + ")";
                        System.out.println(insertComp);
                        sta.executeUpdate(insertComp);
                    }

                    //Insert Sales
                    for (int i = 1; i <= 7; i++) {
                        for (int mat = 1; mat < 39; mat++) {
                            String insertDummySales = "INSERT INTO saleshist VALUES("
                                    + mat
                                    + ", 2014, " + i + ", "
                                    + (int) (100 + Math.floor(Math.random() * 200))
                                    + ")";
                            System.out.println(insertDummySales);
                            sta.executeUpdate(insertDummySales);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else if (choice.equals("b")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
                    Statement sta = (Statement) con.createStatement();

                    System.out.println("Format: mat no, year, month, amt");

                    String ins = "INSERT INTO saleshist VALUES ("
                            + br.readLine()
                            + ","
                            + br.readLine()
                            + ","
                            + br.readLine()
                            + ","
                            + br.readLine()
                            + ")";

                    sta.executeUpdate(ins);

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

        }

    }
}
