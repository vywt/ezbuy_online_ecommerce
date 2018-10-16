package entity;

import entity.Ezuser;
import entity.PurchaseOrder;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-12T17:31:12")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Ezuser> owner;
    public static volatile SingularAttribute<Item, String> itemName;
    public static volatile SingularAttribute<Item, Double> price;
    public static volatile SingularAttribute<Item, Integer> quantityRemaining;
    public static volatile SingularAttribute<Item, Integer> id;
    public static volatile SingularAttribute<Item, Integer> availability;
    public static volatile SingularAttribute<Item, String> itemDescription;
    public static volatile SingularAttribute<Item, Integer> category;
    public static volatile ListAttribute<Item, PurchaseOrder> purchaseOrders;

}