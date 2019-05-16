package com.shibi.app.services.interfaces;

import com.shibi.app.models.security.Role;

import java.util.List;

/**
 * Created by User on 06/06/2018.
 */
public interface IRoleService {

   // List<Role> findAll(List<Long> roleIds) throws Exception;

    void setRolesRepo();


    List<Role> getAll() throws Exception;

    Role get(Long roleId) throws Exception;

    Role create(String name) throws Exception;

    Role update(String name, Long roleId) throws Exception;

    boolean toggle(Long roleId) throws Exception;

}
