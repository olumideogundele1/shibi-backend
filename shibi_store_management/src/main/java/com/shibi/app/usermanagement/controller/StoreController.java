package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.Response;
import com.shibi.app.dto.StoreObjects;
import com.shibi.app.models.Stores;
import com.shibi.app.models.User;
import com.shibi.app.services.impl.StoreService;
import com.shibi.app.services.impl.UserService;
import com.shibi.app.utils.SmtpMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * Created by User on 05/07/2018.
 */

@RestController
@RequestMapping(value = "/stores")
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    private StoreService storeService;
    private UserService userService;
    private SmtpMailSender smtpMailSender;

    @Autowired
    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSmtpMailSender(SmtpMailSender smtpMailSender) {
        this.smtpMailSender = smtpMailSender;
    }

    private String imageName;

    /**
     * create a store api
     */
    @PostMapping(value = "/create-store")
    public ResponseEntity createStore(@Valid @RequestBody StoreObjects request) throws Exception {

        Stores stores = storeService.getStoreByUserId(request.getUserId());
        if(stores != null) {
            return new ResponseEntity(new Response("Store has already being created by user"), HttpStatus.BAD_REQUEST);
        }
      storeService.generateStore(request);
      return new ResponseEntity(new Response("Store is created successfully"), HttpStatus.OK);
    }

    @PostMapping(value = "/create/image")
    public ResponseEntity upload(@PathVariable Long id,
                                 HttpServletResponse response, HttpServletRequest request){
        try {
            Stores stores = storeService.findById(id);
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartHttpServletRequest.getFileNames();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
            String fileName = id+ ".png";
            imageName = fileName;

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File("src/main/resources/static/image/store/"+fileName))
            );
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new Response("upload failed"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new Response("upload Success"), HttpStatus.OK);
    }

    /**
     * edit a store api
     */
    @PutMapping(value = "/edit-store")
    public ResponseEntity editStore(@Valid @RequestBody StoreObjects update) throws Exception {

        if(update == null) {
            return new ResponseEntity(new Response("Request not found"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.getByUserId(update.getUserId());
        if( user == null) {
            return new ResponseEntity(new Response("user not found"), HttpStatus.BAD_REQUEST);
        }

        Stores stores = storeService.findById(update.getStoreId());

        stores.setStoreName(update.getStoreName());
        stores.setStoreLocation(update.getStoreLocation());
        stores.setStoreImage(update.getStoreImage());
//        stores.setUser(userService.getByUserId(user.getId()));
        stores.setUser(user);
        storeService.save(stores);

        return ResponseEntity.ok(stores);
    }



    /**
     * toggle a store api
     */
    @PutMapping(value = "/{id}/toggle")
    public ResponseEntity toggle(@PathVariable Long id ) throws Exception {

        if(id == null) {
            return new ResponseEntity("Incompatible id", HttpStatus.BAD_REQUEST);
        }

        boolean toggle = storeService.toggle(id);

        if(!toggle) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
         return new ResponseEntity(new Response("store is now disabled"), HttpStatus.OK);
    }

    /**
     * get all store api
     */
    @GetMapping(value = "/all-stores")
    public ResponseEntity getAllStores(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                       @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception {
        return ResponseEntity.ok(storeService.findAll(new PageRequest(pageNumber, pageSize)));
    }

    /**
     * get a store api
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getSingleStore(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @GetMapping(value = "/{id}/storeByUser")
    public ResponseEntity getStoreByUser(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByUserId(id));
    }
}
