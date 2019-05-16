package com.shibi.app.services.impl;

import com.shibi.app.models.security.UserRole;
import com.shibi.app.repos.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 27/06/2018.
 */
@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepo;

    @Autowired
    public void setUserRoleRepo(UserRoleRepository userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    public UserRole getUserRoleByUserId(Long userId){
        return userRoleRepo.getByUserId(userId);
    }

    public UserRole save(UserRole userRole){
        return userRoleRepo.save(userRole);
    }
}
