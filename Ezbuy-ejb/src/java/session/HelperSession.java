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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
@Stateless
public class HelperSession implements HelperSessionLocal {
    @PersistenceContext(unitName = "Ezbuy-ejbPU")
    private EntityManager em;

    //USER RELATED
    public Ezuser retrieveUserById(Integer id){
        if (em.find(Ezuser.class, id) != null)
            return em.find(Ezuser.class, id);
        else
            return null;
    }
    
    public Ezuser createEzuser(Ezuser ezuser){
        em.persist(ezuser);
        em.merge(ezuser);
        em.flush();
        
        return ezuser;
    }
    
    public Ezuser updateEzuser(Ezuser ezuser){
        Ezuser old = em.find(Ezuser.class, ezuser.getId());
        
        if (old != null){
            old.setAddress(ezuser.getAddress());
            old.setCartItems(ezuser.getCartItems());
            old.setEmail(ezuser.getEmail());
            old.setOwnItems(ezuser.getOwnItems());
            old.setPasssword(ezuser.getPasssword());
            old.setPurchaseOrders(ezuser.getPurchaseOrders());
            old.setSaleOrders(ezuser.getSaleOrders());
            old.setUsername(ezuser.getUsername());
            old.setIsActivated(ezuser.isIsActivated());
            
            em.merge(old);
            em.flush();
            return old;
        } else
            return null;
        
    }
    
    public Ezuser searchUserByUsername(String username){
        Query q = em.createQuery("SELECT e FROM Ezuser e WHERE e.username LIKE :name");
        q.setParameter("name", username);
        List<Ezuser> userList = q.getResultList();
        if (userList.size() >= 1){
            return userList.get(0);
        }
        return null;
    }
    
    public Ezuser verifyUser(String username, String password){
        Query q = em.createQuery("SELECT e FROM Ezuser e WHERE e.username LIKE :name");
        q.setParameter("name", username);
        List<Ezuser> userList = q.getResultList();
        if (userList.size() == 1){
            if(userList.get(0).getPasssword().equals(password)){
                return userList.get(0);
            }
            else
                return null;
        }
        else
            return null;
    }
    
    public List<Ezuser> getAllEzuser(){
        Query q = em.createQuery("SELECT e FROM Ezuser e");
        return q.getResultList();
    }
    
    //CART RELATED
    public List<Item> getCartItems(Integer userId){
        Ezuser user = em.find(Ezuser.class, userId);
        if (user != null){
            return user.getCartItems();
        } else
            return null;
    }
    
    //ITEM RELATED
    public Item addItem(Item item){
        em.persist(item);
        em.merge(item);
        em.flush();
        
        return item;
    }
    
    public List<Item> getSellerList(Integer sellerId){
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.owner.id = :sellerId");
        q.setParameter("sellerId", sellerId);
        return q.getResultList();
    }
    
    public List<Item> getAllItems(){
        Query q = em.createQuery("SELECT i FROM Item i");
        return q.getResultList();
    }
    
    public List<Item> searchItemById(Integer id){
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    public List<Item> searchItemByName(String name){
        Query q = em.createQuery("SELECT i FROM Item i WHERE LOWER(i.itemName) LIKE :itemName");
        q.setParameter("itemName", "%" + name.toLowerCase() + "%");
        
        return q.getResultList();
    }
    
    public List<Item> searchItemByCategory(Integer catNum){
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.category = :catNum");
        q.setParameter("catNum", catNum);
        return q.getResultList();
    }
    
    public List<Item> searchItemByQuantity(Integer qty){
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.quantityRemaining = :qty");
        q.setParameter("qty", qty);
        return q.getResultList();
    }
    
    public List<Item> searchItemByAvailability(Boolean availability){
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.availability = :availability");
        q.setParameter("availability", availability);
        return q.getResultList();
    }
    
    public Boolean removeItem(Item item){
        Item old = em.find(Item.class, item.getId());
        if (old != null){
            em.remove(old);
            return true; 
        }else{
            return false;
        }
    }
    
    public Item updateItem(Item item){
        Item old = em.find(Item.class, item.getId());
        if (old != null){
            old.setAvailability(item.getAvailability());
            old.setCategory(item.getCategory());
            old.setItemDescription(item.getItemDescription());
            old.setItemName(item.getItemName());
            old.setOwner(item.getOwner());
            old.setPrice(item.getPrice());
            old.setPurchaseOrders(item.getPurchaseOrders());
            old.setQuantityRemaining(item.getQuantityRemaining());
            return old;
        }else
            return null;
    }
    
    //ORDER IN GENERAL
    public PurchaseOrder retrieveOrder(int id){
        PurchaseOrder ret = em.find(PurchaseOrder.class, id);
        if (ret != null){
            return ret;
        }
        return null;
    }
    
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po){
        em.persist(po);
        em.merge(po);
        em.flush();
        
        return po;
    }
    
    
    //PURCHASE ORDER
    public List<PurchaseOrder> getAllPurchaseOrders(int cid){
        Ezuser user = em.find(Ezuser.class, cid);
        if (user == null)
            return null;
        else{
            return user.getPurchaseOrders();
        }
    }
    
    public void updatePurchaseOrder(PurchaseOrder po){
        PurchaseOrder old = em.find(PurchaseOrder.class, po.getId());
        if (old != null){
            old.setFeedback(po.getFeedback());
            old.setRating(po.getRating());
            old.setFeedbacked(po.getFeedbacked());
            em.merge(old);
            em.flush();
        }
    }
    
    //SALE ORDER
    public List<PurchaseOrder> getAllSaleOrders(int cid){
        Ezuser user = em.find(Ezuser.class, cid);
        if (user == null)
            return null;
        else{
            return user.getSaleOrders();
        }
    }
    
    public void updateSaleOrder(PurchaseOrder po){
        PurchaseOrder old = em.find(PurchaseOrder.class, po.getId());
        if (old != null){
           old.setOrderStatus(po.getOrderStatus());
           em.merge(old);
           em.flush();
        }
    }
}
