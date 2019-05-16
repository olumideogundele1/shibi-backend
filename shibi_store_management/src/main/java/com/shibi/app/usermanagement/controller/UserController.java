package com.shibi.app.usermanagement.controller;



import com.shibi.app.models.User;
import com.shibi.app.models.security.*;
import com.shibi.app.services.impl.RoleService;
import com.shibi.app.services.impl.UserRoleService;
import com.shibi.app.services.impl.UserService;
import com.shibi.app.usermanagement.dto.ClientRequest;
import com.shibi.app.usermanagement.dto.Role;
import com.shibi.app.usermanagement.dto.UserRequest;
import com.shibi.app.usermanagement.dto.UserUpdateRequest;
import com.shibi.app.utils.EncryptionUtil;
import com.shibi.app.utils.SecurityUtility;
import com.shibi.app.utils.SmtpMailSender;
import com.shibi.app.usermanagement.dto.*;
import com.shibi.app.usermanagement.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by User on 07/06/2018.
 */
@RestController
@RequestMapping("/user")
public class UserController {



    private RoleService roleService;
    private UserService userService;
    private SmtpMailSender smtpMailSender;

    @Autowired
    private UserRoleService userRoleService;




    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSmtpMailSender(SmtpMailSender smtpMailSender) {
        this.smtpMailSender = smtpMailSender;
    }


    //    @Autowired
//    private ISettingsService iSettingsService;

    @GetMapping(value = "/{id}")
    public ResponseEntity getCurrentUser(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(value = "/all-users")
    public ResponseEntity getAllUsers(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                      @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) throws Exception{
        return ResponseEntity.ok(userService.findAll(new PageRequest(pageNumber,pageSize)));
    }

    @RequestMapping(value = "/createUser", method= RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserRequest request) throws Exception{

        String recoveryCode = EncryptionUtil.generateString(20, true, true);

        // check if user exists
        Optional<User> userObject = userService.findByUsername(request.getUserName());

        if(userObject.isPresent()){
            return new ResponseEntity(new Response("User already exists"), HttpStatus.BAD_REQUEST);
        }

        if(userService.findByEmail(request.getEmail()) != null){
            return new ResponseEntity(new Response("Email already exists"), HttpStatus.BAD_REQUEST);
        }

        //create user if user does not exists
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNumber());

        String password = userService.generatePassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        com.shibi.app.models.security.Role role = new com.shibi.app.models.security.Role();
        role.setRoleId(Long.valueOf("2"));
        role.setName("ROLE_ADMIN");
        Set<UserRole> userRoles =  new HashSet<>();
        userRoles.add(new UserRole(user, role));

//        user.setUserType(UserType.valueOf(request.getType()));

        user = userService.generateUser(user,userRoles);

        userService.sendMail(user,password);

       return ResponseEntity.ok(user);

//        return new ResponseEntity(new Response("Thank you for signing Up, Please check your email for confirmation"), HttpStatus.OK);
    }

    @PostMapping(value = "/create-client")
    public ResponseEntity createClient(@Valid @RequestBody ClientRequest request) throws Exception{
        String recoveryCode = EncryptionUtil.generateString(20, true, true);

        // check if client exists
        Optional<User> userObject = userService.findByUsername(request.getUsername());

        if(userObject.isPresent()){
            return new ResponseEntity(new Response("User already exists"), HttpStatus.BAD_REQUEST);
        }
        if(userService.findByEmail(request.getEmail()) != null){
            return new ResponseEntity(new Response("Email already exists"), HttpStatus.BAD_REQUEST);
        }

        //create client from request being made if client does not exist before
        User client = new User();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setUsername(request.getUsername());
        client.setEmail(request.getEmail());
        client.setEnabled(false);
        client.setPhone(request.getPhoneNumber());

        //set the client password
        String password = userService.generatePassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        client.setPassword(encryptedPassword);

        com.shibi.app.models.security.Role role = new com.shibi.app.models.security.Role();
        role.setRoleId(Long.valueOf("3"));
        role.setName("ROLE_CLIENT");
        Set<UserRole> userRoles =  new HashSet<>();
        userRoles.add(new UserRole(client, role));


//        client.setUserType(UserType.CLIENT);

        client = userService.generateUser(client, userRoles);

        //send mail after creating user to activate user
       userService.sendMail(client, password);

        return ResponseEntity.ok(client);




 }

    @PutMapping(value = "/admin-user-update")
    public ResponseEntity adminUserUpdate(@Valid @RequestBody UserUpdateRequest request) throws Exception {

        if(request == null){
            return new ResponseEntity(new Response("Request not found"), HttpStatus.BAD_REQUEST);
        }

        com.shibi.app.models.security.Role role = roleService.getById(request.getRoleId());
        if(role == null){
            return new ResponseEntity(new Response("Role Id not found"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(request.getId());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

       userService.save(user);

        UserRole userRole = userRoleService.getUserRoleByUserId(user.getId());
        userRole.setRole(role);
        userRoleService.save(userRole);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/{id}/toggle")
    public ResponseEntity toggleUser(@PathVariable Long id) throws Exception {

        if(id == null){
            return new ResponseEntity("Incompatible ID",HttpStatus.BAD_REQUEST);
        }

        boolean toggle = userService.toggle(id);

        if(!toggle){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new Response("user is now disabled"),HttpStatus.OK);
    }

    @PutMapping(value = "/update-user-info")
    public ResponseEntity updateClient(@Valid @RequestBody UserUpdateRequest updateRequest) throws Exception{

        //Get current user's details by id from the database
        User currentUser = userService.findById(updateRequest.getId());

        //Business Logic of getting current user
        if(currentUser == null){
            return new ResponseEntity(new Response("User not found"), HttpStatus.BAD_REQUEST);
        }

        if(userService.findByEmail(updateRequest.getEmail()) != null){
            if(userService.findByEmail(updateRequest.getEmail()).getId() != currentUser.getId()){
                return new ResponseEntity(new Response("Email not found"), HttpStatus.BAD_REQUEST);
            }
        }

        Optional<User> userObject = userService.findByUsername(updateRequest.getUsername());
        User user = new User();
        if(userObject.isPresent()){
            user = userObject.get();
            if(user.getId() != currentUser.getId()){
                return new ResponseEntity(new Response("Username not found"), HttpStatus.BAD_REQUEST);
            }
        }

        //Set current user details into database
        currentUser.setFirstName(updateRequest.getFirstName());
        currentUser.setLastName(updateRequest.getLastName());
        currentUser.setUsername(updateRequest.getUsername());
        currentUser.setEmail(updateRequest.getEmail());
        currentUser.setPhone(updateRequest.getPhone());

        //SAVE INTO DATABASE
        userService.save(currentUser);
        return ResponseEntity.ok(currentUser);
      //  return new ResponseEntity(new Response("User is successfully updated"), HttpStatus.OK);
    }

}
