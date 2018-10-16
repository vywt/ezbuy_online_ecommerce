/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    //relationship with user
    @ManyToOne
    private Ezuser seller;
    @ManyToOne
    private Ezuser buyer;
    
    //Relationship with item
    @OneToOne
    private Item item;
    
    //All other attributes
    private Integer quantity;
    private Integer orderStatus; //1,2,3,4
    private String feedback;
    private BigInteger ccNumber;
    private Double rating;
    private String address;
    private Double price;
    private Boolean feedbacked;

    public PurchaseOrder(){
        
    }
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the seller
     */
    public Ezuser getSeller() {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(Ezuser seller) {
        this.seller = seller;
    }

    /**
     * @return the buyer
     */
    public Ezuser getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(Ezuser buyer) {
        this.buyer = buyer;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the orderStatus
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the ccNumber
     */
    public BigInteger getCcNumber() {
        return ccNumber;
    }

    /**
     * @param ccNumber the ccNumber to set
     */
    public void setCcNumber(BigInteger ccNumber) {
        this.ccNumber = ccNumber;
    }

    /**
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Double rating) {
        this.rating = rating;
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

    /**
     * @return the feedbacked
     */
    public Boolean getFeedbacked() {
        return feedbacked;
    }

    /**
     * @param feedbacked the feedbacked to set
     */
    public void setFeedbacked(Boolean feedbacked) {
        this.feedbacked = feedbacked;
    }

}
