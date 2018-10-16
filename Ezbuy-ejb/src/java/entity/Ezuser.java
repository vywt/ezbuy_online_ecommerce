/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class Ezuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    //rs with items
    @OneToMany
    private List<Item> cartItems;
    @OneToMany(mappedBy = "owner")
    private List<Item> ownItems;
    
    //rs with orders
    @OneToMany(mappedBy = "seller")
    private List<PurchaseOrder> saleOrders;
    @OneToMany(mappedBy = "buyer")
    private List<PurchaseOrder> purchaseOrders;
    
    //Account Type
    private boolean isAdmin;
    private boolean isActivated;
    
    //Login info
    private String username;
    private String passsword;
    
    //Own info
    private String email;
    private String address;
    
    public Ezuser(){
        this.cartItems = new ArrayList<Item>();
        this.ownItems = new ArrayList<Item>();
        this.saleOrders = new ArrayList<PurchaseOrder>();
        this.purchaseOrders = new ArrayList<PurchaseOrder>();
        this.isAdmin = false;
        this.isActivated = false;
    }
    

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the cartItems
     */
    public List<Item> getCartItems() {
        return cartItems;
    }

    /**
     * @param cartItems the cartItems to set
     */
    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * @return the ownItems
     */
    public List<Item> getOwnItems() {
        return ownItems;
    }

    /**
     * @param ownItems the ownItems to set
     */
    public void setOwnItems(List<Item> ownItems) {
        this.ownItems = ownItems;
    }

    /**
     * @return the saleOrders
     */
    public List<PurchaseOrder> getSaleOrders() {
        return saleOrders;
    }

    /**
     * @param saleOrders the saleOrders to set
     */
    public void setSaleOrders(List<PurchaseOrder> saleOrders) {
        this.saleOrders = saleOrders;
    }

    /**
     * @return the purchaseOrders
     */
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    /**
     * @param purchaseOrders the purchaseOrders to set
     */
    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the passsword
     */
    public String getPasssword() {
        return passsword;
    }

    /**
     * @param passsword the passsword to set
     */
    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the isActivated
     */
    public boolean isIsActivated() {
        return isActivated;
    }

    /**
     * @param isActivated the isActivated to set
     */
    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
    
    

   
    
}
