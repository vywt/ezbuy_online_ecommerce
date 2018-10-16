package entity;

import entity.Ezuser;
import entity.Item;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-12T17:31:12")
@StaticMetamodel(PurchaseOrder.class)
public class PurchaseOrder_ { 

    public static volatile SingularAttribute<PurchaseOrder, Ezuser> seller;
    public static volatile SingularAttribute<PurchaseOrder, String> feedback;
    public static volatile SingularAttribute<PurchaseOrder, Item> item;
    public static volatile SingularAttribute<PurchaseOrder, BigInteger> ccNumber;
    public static volatile SingularAttribute<PurchaseOrder, Integer> quantity;
    public static volatile SingularAttribute<PurchaseOrder, String> address;
    public static volatile SingularAttribute<PurchaseOrder, Double> price;
    public static volatile SingularAttribute<PurchaseOrder, Double> rating;
    public static volatile SingularAttribute<PurchaseOrder, Integer> orderStatus;
    public static volatile SingularAttribute<PurchaseOrder, Integer> id;
    public static volatile SingularAttribute<PurchaseOrder, Boolean> feedbacked;
    public static volatile SingularAttribute<PurchaseOrder, Ezuser> buyer;

}