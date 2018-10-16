/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Ezuser;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import session.HelperSessionLocal;

/**
 *
 * @author victor
 */
@Singleton
@Startup
public class initSessionBean {
    @EJB
    private HelperSessionLocal helperSession;
    
    @PostConstruct
    public void initData(){
        Ezuser newUser = new Ezuser();
        
        if (helperSession.getAllEzuser().size() == 0){
            newUser.setAddress("NUS Blk 1");
            newUser.setEmail("nus.edu.sg");
            newUser.setPasssword("admin");
            newUser.setUsername("admin");
            newUser.setIsAdmin(true);

            helperSession.createEzuser(newUser);

            newUser = new Ezuser();

            newUser.setAddress("NUS Blk 1");
            newUser.setEmail("nus.edu.sg");
            newUser.setPasssword("adminUser");
            newUser.setUsername("adminUser");
            newUser.setIsAdmin(false);

            helperSession.createEzuser(newUser);
        }
    }
   
}
