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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author victor
 */
@Entity
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    //rs with owner
    @ManyToOne
    private Ezuser owner;
    
    @OneToMany(mappedBy = "item")
    private List<PurchaseOrder> purchaseOrders;
    
    //Attributes for item
    private String itemName;
    private Integer category; //1,2,3 [Toy, Books, Electronics]
    private Integer availability; //True/False (False with quantity remaining is 0)
    private String itemDescription; //Item description
    private Integer quantityRemaining; //Quantity remaining of product
    private Double price; //Price of item

    public Item(){
        this.purchaseOrders = new ArrayList<PurchaseOrder>();
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the owner
     */
    public Ezuser getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Ezuser owner) {
        this.owner = owner;
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
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the category
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     * @return the availability
     */
    public Integer getAvailability() {
        return availability;
    }

    /**
     * @param availability the availability to set
     */
    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    /**
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return the quantityRemaining
     */
    public Integer getQuantityRemaining() {
        return quantityRemaining;
    }

    /**
     * @param quantityRemaining the quantityRemaining to set
     */
    public void setQuantityRemaining(Integer quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    
    
    
}
