/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import static com.sun.xml.wss.impl.misc.SecurityUtil.toLong;
import ejb.FMSBeanRemote;
import ejb.IslandBeanRemote;
import javax.ejb.EJB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IslandServlet extends HttpServlet {
    @EJB
    private IslandBeanRemote ib;
    private FMSBeanRemote fmsb;
    List<String> data = null;
    private ArrayList<String>staffDetails = new ArrayList<String>();
    String userID = new String();
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("BookRetailSystemServlet: processRequest()");
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            HttpSession session = request.getSession();

            String page = request.getPathInfo();
            System.out.println(page);
            
            if ("/mrp".equals(page)) {
                
            }
            else if ("/saleshistory".equals(page)) {
                List<Integer> items = ib.getManufacturingItems(1);
                List<List> sales = getSalesData(items);
                List<String> forecast = getForecast(items);
                List<String> inventory = getInventory(items);
                request.setAttribute("salesData", sales);
                request.setAttribute("forecastData", forecast);
                request.setAttribute("inventoryData", inventory);
            }
            dispatcher = servletContext.getRequestDispatcher(page);

            if (dispatcher == null) {
                 dispatcher = servletContext.getNamedDispatcher("Error");
                 System.out.println("dispatcher null");
            }
            dispatcher.forward(request, response);
    }
    
    protected List<String> getInventory(List<Integer> items) {
        List<String> inventoryData = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            inventoryData.add(ib.getInventory(items.get(i))+"");
        }
        return inventoryData;
    }
    
    protected List<String> getForecast(List<Integer> items) {
        List<String> forecastData = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            forecastData.add(ib.calculateSalesForecast(items.get(i))+"");
        }
        return forecastData;
    }
    
    protected List<List> getSalesData(List<Integer> items) {
        List<List> salesData = new ArrayList<List>();
        for (int i = 0; i < items.size(); i++) {
            List<String> itemData = new ArrayList<String>();
            itemData.add(ib.getMaterialDescription(items.get(i)));
            
            List<Integer> sales = ib.getSalesHistory(items.get(i), 3);
            for (int sale : sales) {
                itemData.add(sale+"");
            }
            salesData.add(itemData);
        }
        return salesData;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
