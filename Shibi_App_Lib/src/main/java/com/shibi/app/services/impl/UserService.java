package com.shibi.app.services.impl;

import com.shibi.app.caches.SessionManager;
import com.shibi.app.models.User;
import com.shibi.app.models.security.UserRole;
import com.shibi.app.repos.RoleRepository;
import com.shibi.app.repos.UserRepository;
import com.shibi.app.utils.SmtpMailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by User on 06/06/2018.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private UserRepository userRepository;
    private SessionManager sessionManager;
    private SmtpMailSender smtpMailSender;

    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Autowired
    public void setSmtpMailSender(SmtpMailSender smtpMailSender) {
        this.smtpMailSender = smtpMailSender;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Value("${characters}")
    private String characters;

    @Value("${defaultPasswordLength}")
    private int defaultPasswordLength;

    @Value("${email_from}")
    private String fromEmail;


    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User getByUsername (String username) {
        return userRepository.findUserByUsername(username);
    }


    public String generatePassword() throws Exception {
        return RandomStringUtils.random(defaultPasswordLength, characters);
    }


    public String generateUsername(String email) throws Exception {
        return email.split("@")[0].replace(" ","");
    }



    public User save(User user){
        return userRepository.save(user);
    }


    public List<User> getAll() throws Exception {
        return userRepository.findAll();
    }


    public User findById(Long id) throws Exception {
        return userRepository.findOne(id);
    }


//    public Long getAdminUsers(Long id) throws Exception{
//        return userRepository.findAdminUsers(Long.valueOf(id));
//    }

    public List<User> getActivatedUsers(Long id) throws Exception {
        return userRepository.getByActivatedUser(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public Page<User> findAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest);
    }


    public boolean toggle(Long id) throws Exception{

        User user =userRepository.findOne(id);
        if(user != null){
            user.setEnabled(!user.isEnabled());
            sessionManager.deleteSession(user.getSessionId());
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public void deleteUser(Long id){
        userRepository.delete(id);
    }
//
//    public long countByType(UserType userType){
//        synchronized (new Object()) {
//            return userRepository.countByUserType(userType);
//        }
//    }

    public User getByUserId(Long id) {
        return userRepository.getByUser(id);
    }

    @Transactional
    public User generateUser(User user, Set<UserRole> userRoles){
        Optional<User> localUserObject = userRepository.findByUsername(user.getUsername());

        if(localUserObject.isPresent()){
            logger.info("User with username {} already exists, Nothing will be done ", user.getUsername());
        }else{
            for(UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            save(user);
              //  sendMail(user, password);

                return user;
            }
        return null;
    }


    public void sendRecoveryEmail(String email, String link) {
        String details = "<p><a href=\"" + link + "\">Click here to continue</a></p>";
        String[] senderEmail = {email};

        //Send the mail
        smtpMailSender.sendMail(fromEmail, senderEmail, "User Authentication",
                "User Authentication", "Kindly click on the link below to continue. Also note that this link will expire in 24 hours.", details);
    }

    public void sendMail(User user, String password) {

        String details = generateDetails(user, password);
        String[] email = {user.getEmail()};

        //send the mail
        smtpMailSender.sendMail(fromEmail, email, "Shibi Login Credentials",
                "Shibi Login Credentials", "Kindly find below your login credentials:", details );
    }

    private String generateDetails(User user, String password) {
        String details = "";

        details += "<strong>Username :</strong> " +user.getUsername() + "<br/>";
        details += "<strong>Password :</strong> " + password + "<br/>";
//        details += "<strong>User Type :</strong> " + user.getUserType().getValue() + "<br/>";

        return details;
    }

}
