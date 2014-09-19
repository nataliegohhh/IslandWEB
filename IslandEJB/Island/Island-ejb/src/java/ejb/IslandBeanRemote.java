/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Owner
 */
@Remote
public interface IslandBeanRemote {
    int add();
    List<Integer> getManufacturingItems(int mfgId);
    List<Integer> getSalesHistory(int matNo, int period);
    int calculateSalesForecast(int matNo);
    String getMaterialDescription(int matno);
    int getInventory(int matNo);
}
