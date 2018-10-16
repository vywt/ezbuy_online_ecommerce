/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Ezuser;
import entity.Item;
import entity.PurchaseOrder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import session.HelperSessionLocal;

/**
 *
 * @author victor
 */
@ManagedBean
@SessionScoped
public class HelperManagedBean {

    //<---------------------ATTRIBUTES-------------------->
    @EJB
    private HelperSessionLocal helperSession;

    //Session Info (User)
    private Ezuser ezuser;

    //Login Form
    private String ezusername;
    private String ezpassword;

    //View All Users
    private List<Ezuser> allUsers;

    //For Registration (Form)
    private String registrationUsername;
    private String registrationPassword;
    private String registrationEmail;
    private String registrationAddress;

    //For Edit Profile (Form)
    private String editUsername;
    private String editPassword;
    private String editEmail;
    private String editAddress;

    //For Adding Item (Form)
    private String aiFormProductName;
    private String aiFormDescription;
    private Integer aiQuantity;
    private Integer aiCategory;
    private Double aiPrice;
    private Integer aiAvailability;

    //For Editing Item (Form)
    private Integer editId;
    private String eFormProductName;
    private String eFormDescription;
    private Integer eQuantity;
    private Integer eCategory;
    private Double ePrice;
    private Integer eAvailability;

    //For Seller Items List
    private List<Item> sellerList;

    //For all items list
    private List<Item> allItemList;

    //For User's Purchase Order List (View Purchase Order)
    private List<PurchaseOrder> allPurchaseOrders;

    //For User's Seller Order List (View Sale Orders)
    private List<PurchaseOrder> allSaleOrders;

    //For User's Cart Items
    private List<Item> cartItems;

    //For Checking Out
    private BigInteger ccNumber;

    //For Item Status
    private Integer newOrderStatus;

    //For Feedback Form
    private String ffeedback;
    private Double frating;

    //For Search Function
    private String searchString;
    private String searchStringGlobal;
    private Integer searchType;
    private Integer searchCat;
    private Integer searchQuantityGlobal;

    //<--------------------METHODS----------------------->
    public HelperManagedBean() {
    }

    //init
    public void reloadList() {
        System.err.println("reloading list!");
        if (ezuser != null) {
            System.err.println("reloading list! - in ezuser != null");
            setSellerList(helperSession.getSellerList(ezuser.getId()));
            setEzuser(helperSession.retrieveUserById(ezuser.getId()));
            setAllPurchaseOrders(helperSession.getAllPurchaseOrders(ezuser.getId()));
            setAllSaleOrders(helperSession.getAllSaleOrders(ezuser.getId()));
            setCartItems(helperSession.getCartItems(ezuser.getId()));
            newOrderStatus = 1;
            loadEditFields();
            ccNumber = null;
            ffeedback = "";
            frating = null;
        }

        setAllUsers(helperSession.getAllEzuser());
        setAllItemList(helperSession.getAllItems());
        searchType = 1;
        searchCat = 1;
        searchQuantityGlobal = 1;
    }

    //Load Profile Edit fields
    public void loadEditFields() {
        System.err.println("loading fields!");
        setEditAddress(ezuser.getAddress());
        setEditEmail(ezuser.getEmail());
        setEditPassword(ezuser.getPasssword());
        setEditUsername(ezuser.getUsername());
    }

    //<--------------------GENERAL----------------------->
    //For Search
    public void handleSearch() {
        System.err.println("handle search error try");
        initSearch();
    }

    //For Global Search
    public void handleSearchGlobal() {
        System.err.println("handle global search");
        initSearchGlobal();
    }

    public void initSearchGlobal() {
        if (searchStringGlobal == null || searchStringGlobal.equals("")) {
            reloadList();
        } else {
            setAllItemList(helperSession.searchItemByName(searchStringGlobal));
        }
    }

//    public void handleQuantitySearchGlobal(){
//        Integer qty = searchQuantityGlobal;
//        if (qty < 0){
//            reloadList();
//        } else {
//            setAllItemList(helperSession.searchItemByQuantity(searchQuantityGlobal));
//        }
//    }
//    
//    public void handleCatSearch(){
//        Integer qty = searchCat;
//        if (qty < 0){
//            reloadList();
//        } else {
//            setAllItemList(helperSession.searchItemByCategory(searchCat));
//        }
//    }
    public void initSearch() {
        if (searchString == null || searchString.equals("")) {
            reloadList();
        } else {
            setSellerList(findSellerList(searchString));
        }
    }

    public List<Item> findSellerList(String searchString) {
        List<Item> allResult = helperSession.searchItemByName(searchString);
        System.err.println("oSize of list is" + allResult.size());
        List<Item> result = new ArrayList<Item>();
        for (Item item : allResult) {
            if (item.getOwner().getId() == ezuser.getId()) {
                result.add(item);
            }
        }

        return result;
    }

    //Test Login: To avoid entering details into login screen
    public String testLogin() {
        reloadList();
        ezuser = helperSession.getAllEzuser().get(0);
        reloadList();
        return ("/userPages/index.xhtml?redirect=true");
    }

    //For Login
    public String login() {
        Ezuser ezuserCurr = helperSession.verifyUser(getEzusername(), getEzpassword());

        if (ezuserCurr != null) {
            this.setEzuser(ezuserCurr);
            reloadList();
            if (getEzuser().isIsAdmin()) {
                System.err.println("TEST ADMIN");
                return "/adminPages/index.xhtml?redirect=true";
            } else if (getEzuser().isIsActivated()) {
                System.err.println("TEST ACTIVATED");
                return "/userPages/index.xhtml?redirect=true";
            } else {
                System.err.println("TEST NOT ACTIVATED");
                return "notActivated.xhtml?redirect=true";
            }
        } else {
            return "indexWrongPassword.xhtml?redirect=true";
        }
    }

    //For Logout
    public String logout() {
        setEzuser(null);
        System.err.println("TEST LOGOUT");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(HelperManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        setEzusername("");
        setEzpassword("");
        return "/index.xhtml?redirect=true";
    }

    //View All Users (Sellers/Buyers)
    public void loadAllUsers() {
        setAllUsers(helperSession.getAllEzuser());
    }

    //Register
    public void registerUser() {
        Ezuser ezuserRegister = new Ezuser();
        ezuserRegister.setAddress(getRegistrationAddress());
        ezuserRegister.setEmail(getRegistrationEmail());
        ezuserRegister.setPasssword(getRegistrationPassword());
        ezuserRegister.setUsername(getRegistrationUsername());

        if (helperSession.searchUserByUsername(getRegistrationUsername()) != null) {
            setRegistrationAddress("");
            setRegistrationEmail("");
            setRegistrationPassword("");
            setRegistrationUsername("");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Fail", "Username already exists!"));
            return;
        };

        helperSession.createEzuser(ezuserRegister);

        setRegistrationAddress("");
        setRegistrationEmail("");
        setRegistrationPassword("");
        setRegistrationUsername("");
    }

    //Edit Profile
    public void updateUser() {

        getEzuser().setAddress(getEditAddress());
        getEzuser().setEmail(getEditEmail());
        getEzuser().setPasssword(getEditPassword());
        getEzuser().setUsername(getEditUsername());

        helperSession.updateEzuser(ezuser);

        reloadList();
    }

    //<--------------------CART----------------------->
    public void addToCart(Integer itemId) {
        Item item = null;
        boolean temp = false;
        boolean ownItem = false;
        boolean notAvail = false;

        List<Item> itemsTest = helperSession.searchItemById(itemId);
        if (itemsTest.size() == 1) {
            item = itemsTest.get(0);
        } else {
            return;
        }

        if (item.getQuantityRemaining() <= 0) {
            notAvail = true;
        }

        if (item.getOwner().getId() == getEzuser().getId()) {
            ownItem = true;
        }

        Ezuser user = helperSession.retrieveUserById(ezuser.getId());

        List<Item> cartItemsTemp = getCartItems();
        for (Item itemthis : cartItemsTemp) {

            if (itemthis.getId() == itemId) {
                temp = true;
            }
        }

        if (notAvail) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Fail", "Item out of stock!"));
            return;
        }

        if (ownItem) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Fail", "Cannot add own item!"));
            return;
        }

        if (temp) {
            System.err.println("REACHED" + itemId);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Fail", "Item has already been added to cart!"));
            return;
        } else {
            System.err.println("HERE" + itemId);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Success", "Item added successfully!"));
            System.err.println("EVEN HERE" + itemId);
            user.getCartItems().add(item);
            helperSession.updateEzuser(user);
            reloadList();
        }
    }

    public void checkOutCart() {
        BigInteger ccNumberTemp = ccNumber;
        reloadList();
        if (ccNumberTemp.equals(null)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Fail", "Please enter Credit Card Number!"));
            return;
        }

        System.err.println("Check Out is called!");
        List<Item> checkOutList = ezuser.getCartItems();
        for (Item item : checkOutList) {
            PurchaseOrder po = new PurchaseOrder();
            po.setAddress(ezuser.getAddress());
            po.setBuyer(ezuser);
            po.setCcNumber(ccNumberTemp);
            po.setFeedback("");
            po.setItem(item);
            po.setOrderStatus(1);
            po.setRating(null);
            po.setPrice(item.getPrice());
            po.setSeller(item.getOwner());
            po.setQuantity(1);
            po.setFeedbacked(false);

            po = helperSession.createPurchaseOrder(po);

            item.getPurchaseOrders().add(po);
            item.setQuantityRemaining(item.getQuantityRemaining() - 1);
            helperSession.updateItem(item);

            ezuser.getPurchaseOrders().add(po);
            helperSession.updateEzuser(ezuser);

            Ezuser seller = item.getOwner();
            seller.getSaleOrders().add(po);
            helperSession.updateEzuser(seller);
        }

        ezuser.setCartItems(new ArrayList<Item>());
        helperSession.updateEzuser(ezuser);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Success", "Cart checked out successfully!"));

        reloadList();
    }

    //<--------------------FEEDBACK----------------------->
    public void addFeedback(int id) {
        PurchaseOrder po = helperSession.retrieveOrder(id);
        po.setFeedback(ffeedback);
        po.setRating(frating);
        po.setFeedbacked(true);
        helperSession.updatePurchaseOrder(po);

        setFfeedback("");
        setFrating(null);

    }

    //<--------------------ITEM----------------------->
    //Add Item
    public void addItem() {
        Item item = new Item();
        item.setAvailability(aiAvailability);
        item.setCategory(aiCategory);
        item.setItemDescription(aiFormDescription);
        item.setItemName(aiFormProductName);
        item.setOwner(ezuser);
        item.setPrice(aiPrice);
        item.setQuantityRemaining(aiQuantity);

        helperSession.addItem(item);

        setAiAvailability(null);
        setAiCategory(null);
        setAiFormDescription("");
        setAiFormProductName("");
        setAiPrice(null);
        setAiQuantity(null);
        reloadList();
    }

    //Remove Item
    public void deleteItem(Integer itemId) {
        boolean isInCart = false;
        List<Item> result = helperSession.searchItemById(itemId);
        Item itemDelete = result.get(0);
        Ezuser userDelete = helperSession.retrieveUserById(itemDelete.getOwner().getId());

        for (Ezuser user : allUsers) {
            for (Item cartItem : user.getCartItems()) {
                if (cartItem.getId() == itemDelete.getId()) {
                    System.err.println("In Delete Item");
                    isInCart = true;
                }
            }
        }

        if (isInCart) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Failed", "Item is in someone's cart!"));

            reloadList();
        } else if (itemDelete.getPurchaseOrders().isEmpty()) {

            //Remove from user's own items
            userDelete.getOwnItems().remove(itemDelete);
            helperSession.updateEzuser(userDelete);

            //Remove from any user who has the item in cart
            for (Ezuser inUser : allUsers) {
                if (inUser.getCartItems().contains(itemDelete)) {
                    inUser.getCartItems().remove(itemDelete);
                    helperSession.updateEzuser(inUser);
                }

            }

            helperSession.removeItem(itemDelete);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Success", "Item Deleted!"));

            reloadList();

        } else {

            itemDelete.setQuantityRemaining(0);
            helperSession.updateItem(itemDelete);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Success", "Item was ordered. Quantity set to 0!"));
            reloadList();

        }
    }

    //Edit an item 
    public void editItem(Integer itemId) {
        List<Item> result = helperSession.searchItemById(itemId);
        Item itemEdit = result.get(0);

        itemEdit.setAvailability(eAvailability);
        itemEdit.setCategory(eCategory);
        itemEdit.setItemDescription(eFormDescription);
        itemEdit.setItemName(eFormProductName);
        itemEdit.setPrice(ePrice);
        itemEdit.setQuantityRemaining(eQuantity);

        helperSession.updateItem(itemEdit);
        reloadList();

        seteAvailability(null);
        seteCategory(null);
        seteFormDescription("");
        seteFormProductName("");
        setePrice(null);
        seteQuantity(null);
    }

    //Load the item in edit view
    public void loadItem(Integer itemId) {
        System.err.println("Item Id is " + itemId);
        List<Item> result = helperSession.searchItemById(itemId);
        Item itemEdit = result.get(0);

        seteAvailability(itemEdit.getAvailability());
        seteCategory(itemEdit.getCategory());
        seteFormDescription(itemEdit.getItemDescription());
        seteFormProductName(itemEdit.getItemName());
        setePrice(itemEdit.getPrice());
        seteQuantity(itemEdit.getQuantityRemaining());
    }

    //<--------------------ORDER----------------------->
    public void updatePurchaseOrder(int id) {
        PurchaseOrder poUpdate = helperSession.retrieveOrder(id);
        poUpdate.setFeedback(ffeedback);
        poUpdate.setRating(frating);
        poUpdate.setFeedbacked(true);
        helperSession.updatePurchaseOrder(poUpdate);
        reloadList();
    }

    public void updateSaleOrder(int id) {
        System.err.println(newOrderStatus);
        PurchaseOrder poUpdate = helperSession.retrieveOrder(id);
        poUpdate.setOrderStatus(newOrderStatus);
        helperSession.updateSaleOrder(poUpdate);
        reloadList();
    }

    //<--------------------ADMIN----------------------->
    public void deactivateUser(int id) {
        Ezuser user = helperSession.retrieveUserById(id);
        user.setIsActivated(false);
        helperSession.updateEzuser(user);
        reloadList();
    }

    //Activate User
    public void activateUser(int id) {
        Ezuser user = helperSession.retrieveUserById(id);
        user.setIsActivated(true);
        helperSession.updateEzuser(user);
        reloadList();
    }

    //<--------------------GETTER / SETTER----------------------->
    /**
     * @return the ezuser
     */
    /**
     * @return the sellerList
     */
    public List<Item> getSellerList() {
        return sellerList;
    }

    /**
     * @param sellerList the sellerList to set
     */
    public void setSellerList(List<Item> sellerList) {
        this.sellerList = sellerList;
    }

    public Ezuser getEzuser() {
        return ezuser;
    }

    /**
     * @param ezuser the ezuser to set
     */
    public void setEzuser(Ezuser ezuser) {
        this.ezuser = ezuser;
    }

    /**
     * @return the ezusername
     */
    public String getEzusername() {
        return ezusername;
    }

    /**
     * @param ezusername the ezusername to set
     */
    public void setEzusername(String ezusername) {
        this.ezusername = ezusername;
    }

    /**
     * @return the ezpassword
     */
    public String getEzpassword() {
        return ezpassword;
    }

    /**
     * @param ezpassword the ezpassword to set
     */
    public void setEzpassword(String ezpassword) {
        this.ezpassword = ezpassword;
    }

    /**
     * @return the allUsers
     */
    public List<Ezuser> getAllUsers() {
        return allUsers;
    }

    /**
     * @param allUsers the allUsers to set
     */
    public void setAllUsers(List<Ezuser> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * @return the registrationUsername
     */
    public String getRegistrationUsername() {
        return registrationUsername;
    }

    /**
     * @param registrationUsername the registrationUsername to set
     */
    public void setRegistrationUsername(String registrationUsername) {
        this.registrationUsername = registrationUsername;
    }

    /**
     * @return the registrationPassword
     */
    public String getRegistrationPassword() {
        return registrationPassword;
    }

    /**
     * @param registrationPassword the registrationPassword to set
     */
    public void setRegistrationPassword(String registrationPassword) {
        this.registrationPassword = registrationPassword;
    }

    /**
     * @return the registrationEmail
     */
    public String getRegistrationEmail() {
        return registrationEmail;
    }

    /**
     * @param registrationEmail the registrationEmail to set
     */
    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    /**
     * @return the registrationAddress
     */
    public String getRegistrationAddress() {
        return registrationAddress;
    }

    /**
     * @param registrationAddress the registrationAddress to set
     */
    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    /**
     * @return the aiFormProductName
     */
    public String getAiFormProductName() {
        return aiFormProductName;
    }

    /**
     * @param aiFormProductName the aiFormProductName to set
     */
    public void setAiFormProductName(String aiFormProductName) {
        this.aiFormProductName = aiFormProductName;
    }

    /**
     * @return the aiFormDescription
     */
    public String getAiFormDescription() {
        return aiFormDescription;
    }

    /**
     * @param aiFormDescription the aiFormDescription to set
     */
    public void setAiFormDescription(String aiFormDescription) {
        this.aiFormDescription = aiFormDescription;
    }

    /**
     * @return the aiQuantity
     */
    public Integer getAiQuantity() {
        return aiQuantity;
    }

    /**
     * @param aiQuantity the aiQuantity to set
     */
    public void setAiQuantity(Integer aiQuantity) {
        this.aiQuantity = aiQuantity;
    }

    /**
     * @return the aiCategory
     */
    public Integer getAiCategory() {
        return aiCategory;
    }

    /**
     * @param aiCategory the aiCategory to set
     */
    public void setAiCategory(Integer aiCategory) {
        this.aiCategory = aiCategory;
    }

    /**
     * @return the aiPrice
     */
    public Double getAiPrice() {
        return aiPrice;
    }

    /**
     * @param aiPrice the aiPrice to set
     */
    public void setAiPrice(Double aiPrice) {
        this.aiPrice = aiPrice;
    }

    /**
     * @return the aiAvailability
     */
    public Integer getAiAvailability() {
        return aiAvailability;
    }

    /**
     * @param aiAvailability the aiAvailability to set
     */
    public void setAiAvailability(Integer aiAvailability) {
        this.aiAvailability = aiAvailability;
    }

    /**
     * @return the eFormProductName
     */
    public String geteFormProductName() {
        return eFormProductName;
    }

    /**
     * @param eFormProductName the eFormProductName to set
     */
    public void seteFormProductName(String eFormProductName) {
        this.eFormProductName = eFormProductName;
    }

    /**
     * @return the eFormDescription
     */
    public String geteFormDescription() {
        return eFormDescription;
    }

    /**
     * @param eFormDescription the eFormDescription to set
     */
    public void seteFormDescription(String eFormDescription) {
        this.eFormDescription = eFormDescription;
    }

    /**
     * @return the eQuantity
     */
    public Integer geteQuantity() {
        return eQuantity;
    }

    /**
     * @param eQuantity the eQuantity to set
     */
    public void seteQuantity(Integer eQuantity) {
        this.eQuantity = eQuantity;
    }

    /**
     * @return the eCategory
     */
    public Integer geteCategory() {
        return eCategory;
    }

    /**
     * @param eCategory the eCategory to set
     */
    public void seteCategory(Integer eCategory) {
        this.eCategory = eCategory;
    }

    /**
     * @return the ePrice
     */
    public Double getePrice() {
        return ePrice;
    }

    /**
     * @param ePrice the ePrice to set
     */
    public void setePrice(Double ePrice) {
        this.ePrice = ePrice;
    }

    /**
     * @return the eAvailability
     */
    public Integer geteAvailability() {
        return eAvailability;
    }

    /**
     * @param eAvailability the eAvailability to set
     */
    public void seteAvailability(Integer eAvailability) {
        this.eAvailability = eAvailability;
    }

    /**
     * @return the allPurchaseOrders
     */
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return allPurchaseOrders;
    }

    /**
     * @param allPurchaseOrders the allPurchaseOrders to set
     */
    public void setAllPurchaseOrders(List<PurchaseOrder> allPurchaseOrders) {
        this.allPurchaseOrders = allPurchaseOrders;
    }

    /**
     * @return the allSaleOrders
     */
    public List<PurchaseOrder> getAllSaleOrders() {
        return allSaleOrders;
    }

    /**
     * @param allSaleOrders the allSaleOrders to set
     */
    public void setAllSaleOrders(List<PurchaseOrder> allSaleOrders) {
        this.allSaleOrders = allSaleOrders;
    }

    /**
     * @return the editUsername
     */
    public String getEditUsername() {
        return editUsername;
    }

    /**
     * @param editUsername the editUsername to set
     */
    public void setEditUsername(String editUsername) {
        this.editUsername = editUsername;
    }

    /**
     * @return the editPassword
     */
    public String getEditPassword() {
        return editPassword;
    }

    /**
     * @param editPassword the editPassword to set
     */
    public void setEditPassword(String editPassword) {
        this.editPassword = editPassword;
    }

    /**
     * @return the editEmail
     */
    public String getEditEmail() {
        return editEmail;
    }

    /**
     * @param editEmail the editEmail to set
     */
    public void setEditEmail(String editEmail) {
        this.editEmail = editEmail;
    }

    /**
     * @return the editAddress
     */
    public String getEditAddress() {
        return editAddress;
    }

    /**
     * @param editAddress the editAddress to set
     */
    public void setEditAddress(String editAddress) {
        this.editAddress = editAddress;
    }

    /**
     * @return the allItemList
     */
    public List<Item> getAllItemList() {
        return allItemList;
    }

    /**
     * @param allItemList the allItemList to set
     */
    public void setAllItemList(List<Item> allItemList) {
        this.allItemList = allItemList;
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
     * @return the ffeedback
     */
    public String getFfeedback() {
        return ffeedback;
    }

    /**
     * @param ffeedback the ffeedback to set
     */
    public void setFfeedback(String ffeedback) {
        this.ffeedback = ffeedback;
    }

    /**
     * @return the frating
     */
    public Double getFrating() {
        return frating;
    }

    /**
     * @param frating the frating to set
     */
    public void setFrating(Double frating) {
        this.frating = frating;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the newOrderStatus
     */
    public Integer getNewOrderStatus() {
        return newOrderStatus;
    }

    /**
     * @param newOrderStatus the newOrderStatus to set
     */
    public void setNewOrderStatus(Integer newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    /**
     * @return the editId
     */
    public Integer getEditId() {
        return editId;
    }

    /**
     * @param editId the editId to set
     */
    public void setEditId(Integer editId) {
        this.editId = editId;
    }

    /**
     * @return the searchStringGlobal
     */
    public String getSearchStringGlobal() {
        return searchStringGlobal;
    }

    /**
     * @param searchStringGlobal the searchStringGlobal to set
     */
    public void setSearchStringGlobal(String searchStringGlobal) {
        this.searchStringGlobal = searchStringGlobal;
    }

    /**
     * @return the searchType
     */
    public Integer getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the searchCat
     */
    public Integer getSearchCat() {
        return searchCat;
    }

    /**
     * @param searchCat the searchCat to set
     */
    public void setSearchCat(Integer searchCat) {
        this.searchCat = searchCat;
    }

    /**
     * @return the searchQuantityGlobal
     */
    public Integer getSearchQuantityGlobal() {
        return searchQuantityGlobal;
    }

    /**
     * @param searchQuantityGlobal the searchQuantityGlobal to set
     */
    public void setSearchQuantityGlobal(Integer searchQuantityGlobal) {
        this.searchQuantityGlobal = searchQuantityGlobal;
    }

}
