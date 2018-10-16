/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Ezuser;
import entity.Item;
import entity.PurchaseOrder;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author victor
 */
@Local
public interface HelperSessionLocal {
    
//    public List<Item> searchItemByQuantity(Integer qty);

    public Ezuser updateEzuser(Ezuser ezuser);

    public Ezuser createEzuser(Ezuser ezuser);

    public Ezuser verifyUser(String username, String password);

    public Item addItem(Item item);

    public List<Item> searchItemByName(String name);

    public List<Item> searchItemByCategory(Integer catNum);

    public List<Item> searchItemByAvailability(Boolean availability);

    public Boolean removeItem(Item item);

    public Item updateItem(Item item);

    public List<PurchaseOrder> getAllPurchaseOrders(int cid);

    public void updatePurchaseOrder(PurchaseOrder po);

    public List<PurchaseOrder> getAllSaleOrders(int cid);

    public void updateSaleOrder(PurchaseOrder po);

    public List<Item> getAllItems();

    public List<Ezuser> getAllEzuser();

    public Ezuser retrieveUserById(Integer id);

    public List<Item> getSellerList(Integer sellerId);

    public List<Item> searchItemById(Integer id);

    public PurchaseOrder retrieveOrder(int id);

    public List<Item> getCartItems(Integer userId);

    public PurchaseOrder createPurchaseOrder(PurchaseOrder po);

    public Ezuser searchUserByUsername(String username);
    
}
