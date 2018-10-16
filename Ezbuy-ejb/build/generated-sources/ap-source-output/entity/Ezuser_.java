package entity;

import entity.Item;
import entity.PurchaseOrder;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-12T17:31:12")
@StaticMetamodel(Ezuser.class)
public class Ezuser_ { 

    public static volatile ListAttribute<Ezuser, Item> ownItems;
    public static volatile SingularAttribute<Ezuser, String> address;
    public static volatile SingularAttribute<Ezuser, String> passsword;
    public static volatile ListAttribute<Ezuser, PurchaseOrder> saleOrders;
    public static volatile SingularAttribute<Ezuser, Integer> id;
    public static volatile SingularAttribute<Ezuser, Boolean> isAdmin;
    public static volatile SingularAttribute<Ezuser, Boolean> isActivated;
    public static volatile ListAttribute<Ezuser, Item> cartItems;
    public static volatile ListAttribute<Ezuser, PurchaseOrder> purchaseOrders;
    public static volatile SingularAttribute<Ezuser, String> email;
    public static volatile SingularAttribute<Ezuser, String> username;

}