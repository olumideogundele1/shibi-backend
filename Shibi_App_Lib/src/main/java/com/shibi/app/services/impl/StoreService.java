package com.shibi.app.services.impl;

import com.shibi.app.dto.StoreObjects;
import com.shibi.app.models.Stores;
import com.shibi.app.models.User;
import com.shibi.app.repos.StoreRepository;
import com.shibi.app.utils.SmtpMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by User on 04/07/2018.
 */
@Service
public class StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @Value("${email_from}")
    private String fromEmail;

    /**
    generate a store
    * */
    @Transactional
    public Stores generateStore(StoreObjects storeObjects) throws Exception {

        //check if store exists

        Optional<Stores> localStoreObject = storeRepository.findByStoreName(storeObjects.getStoreName());

        if (localStoreObject.isPresent()) {
            logger.info("Store with storename {} aready exists", storeObjects.getStoreName());
        }
//        User user = new User();
//        Stores getStoreByUser = getStoreByUserId(storeObjects.getUserId());
//        if (getStoreByUser != null) {
//
//        }

        User user = userService.getByUserId(storeObjects.getUserId());

        Stores stores = new Stores();
        stores.setStoreName(storeObjects.getStoreName());
        stores.setStoreLocation(storeObjects.getStoreLocation());
        stores.setStoreImage(storeObjects.getStoreImage());
        stores.setUser(user);
//        stores.setUser(userService.getByUserId(user.getId()));

        save(stores);
        sendMail(stores, user);

        return  stores;
}

    /**
     find store by userId
     * */
    public Stores getStoreByUserId(Long id) throws Exception{
        return storeRepository.findStoresByUserId(id);
    }


    /**
    toggle a store
    * */
    public boolean toggle(Long id) throws Exception {
        Stores stores = storeRepository.findOne(id);

        if(stores != null){
            stores.setEnabled(!stores.isEnabled());
            save(stores);
            return true;
        }else{
            return false;
        }
    }

    /**
    get all store
    * */
    public Page<Stores> findAll(PageRequest pageRequest) {
        return storeRepository.findAll(pageRequest);
    }

    /**
    get a store
    * */
    public Stores findById(Long id ) throws Exception {
        return storeRepository.findOne(id);
    }

     /**
   delete a store
    * */
    public void deleteUser(Long id) {
        storeRepository.delete(id);
    }

    /**
     Save a store
     * */
    public Stores save(Stores stores){
       return storeRepository.save(stores);
    }

    /**
     send a mail to client to notify new store purchase
     * */
    public void sendMail(Stores stores, User user) {
        String details = this.generateDetails(stores);
        User userObject = userService.getByUserId(user.getId());
        String[] email = new String[]{userObject.getEmail()};
        this.smtpMailSender.sendMail(this.fromEmail, email, "Shibi  Store Approval", "Welcome to Shibi App Store", "Thank you for registering:", details);
    }

    private String generateDetails(Stores stores) {

        String details = "";
        details += "<strong>Thank you {} for subscribing to the Shibi App store" + "/n Your trial period is for 30 days </strong>" + stores.getStoreName() + "<br>";

        return details;
    }

}
