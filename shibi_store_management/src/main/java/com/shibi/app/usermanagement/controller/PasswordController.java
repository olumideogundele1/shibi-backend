package com.shibi.app.usermanagement.controller;

import com.shibi.app.config.RedisConfiguration;
import com.shibi.app.dto.UserDetail;
import com.shibi.app.models.User;
import com.shibi.app.services.impl.UserService;
import com.shibi.app.usermanagement.dto.PasswordRequest;
import com.shibi.app.usermanagement.dto.ResetUserPasswordRequest;
import com.shibi.app.utils.EncryptionUtil;
import com.shibi.app.utils.SecurityUtility;
import com.shibi.app.usermanagement.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by User on 14/06/2018.
 */
@RestController
@RequestMapping("/password")
public class PasswordController {

private static Logger logger = LoggerFactory.getLogger(PasswordController.class);

        private UserService userService;
        private RedisConfiguration redisConfiguration;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
        this.redisConfiguration = redisConfiguration;
    }

    @PutMapping(value = "/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetUserPasswordRequest request) throws Exception {

        User user = userService.findByEmail(request.getEmail());

        String recoveryCode = EncryptionUtil.generateString(20,true,true);

        if(user == null){
            return new ResponseEntity(new Response("user not found"), HttpStatus.BAD_REQUEST);
        }

        //generated Password
        String password = userService.generatePassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        userService.save(user);

        //send mail for the user to reset his password
        userService.sendRecoveryEmail(user.getEmail(),(request.getPath() + recoveryCode));

        return new ResponseEntity(new Response("Password Reset is successful. please do check your mail"),HttpStatus.OK);
    }

    @PutMapping(value = "/updateUserPassword")
    public ResponseEntity updatePassword(@Valid @RequestBody PasswordRequest request, @RequestHeader("Authorization") String token) throws Exception{

        UserDetail userDetail = redisConfiguration.decodeToken(token);

        if (token == null || token.isEmpty()){
            return new ResponseEntity(new Response("token not found"),HttpStatus.NOT_FOUND);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())){
            return new ResponseEntity(new Response("passwords do not match"),HttpStatus.BAD_REQUEST);
        }

        User initialUser = userService.findById(userDetail.getId());
        if(initialUser == null){
            return new ResponseEntity(new Response("user not found"),HttpStatus.NOT_FOUND);
        }
        BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();

        if(passwordEncoder.matches(request.getCurrentPassword(),initialUser.getPassword())){
            initialUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }else {
            return new ResponseEntity(new Response("Incorrect Password"), HttpStatus.BAD_REQUEST);
        }

        userService.save(initialUser);





        return new ResponseEntity(new Response("password changed successfully"),HttpStatus.OK);
    }

}
