package com.shibi.app.usermanagement.config;

import com.shibi.app.models.User;
import com.shibi.app.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by User on 26/06/2018.
 */
@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional <User> userObject = userRepository.findByUsername(username);
//        User userObject = userRepository.findUserByUsername(username);

        if(!userObject.isPresent()){
            LOGGER.warn("Username {} is not found", username);
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return userObject.get();
    }
}
