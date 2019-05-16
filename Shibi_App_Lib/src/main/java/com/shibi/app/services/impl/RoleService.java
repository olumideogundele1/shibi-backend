package com.shibi.app.services.impl;

import com.shibi.app.models.security.Role;
import com.shibi.app.repos.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User on 06/06/2018.
 */
@Service
public class RoleService  {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

//    @Override
//    public List<Role> getAll() throws Exception{
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Role get(Long roleId){
//        return roleRepository.findOne(roleId);
//    }
//
//    @Override
//    public Role create(String roleName) throws Exception{
//        Role role = new Role();
//        role.setRoleName(roleName);
//        role.setActivated(true);
//        role = roleRepository.save(role);
//
//        if(role != null){
//            return role;
//        }
//        return new Role();
//    }
//
//    @Override
//    public Role update(String roleName, Long roleId) throws Exception{
//
//        Role role = roleRepository.findOne(roleId);
//        if(role != null){
//            role.setRoleName(roleName);
//            return roleRepository.save(role);
//        }
//
//        return new Role();
//    }
//
//    @Override
//    public boolean toggle(Long roleId) throws Exception {
//
//
//        Role role = roleRepository.findOne(roleId);
//        if (role != null) {
//            role.setActivated(!role.isActivated());
//            roleRepository.save(role);
//            return true;
//        }
//
//        return false;
//    }

    @Autowired
    public void setRolesRepo(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Page<Role> get(Pageable pageable){
        return roleRepository.findAll(pageable);
    }

//   public List<Role> getActivated(UserType userType){
//        return roleRepository.getAllActivated(userType);
//    }

    public Role getById(Long roleId){
        return roleRepository.findOne(roleId);
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }

    public Role getByName(String name){
        return roleRepository.findByName(name);
    }

    public Role getByNameAndNotId(String name, Long roleId){
        return roleRepository.getByNameAndNotId(name, roleId);
    }

//    public Role toggle(Long roleId){
//
//        if (roleId == null){
//            return new Role();
//        }
//
//        roleRepository.toggle(roleId);
//        return getById(roleId);
//    }

    public List<Role> getAllClients(){
        return roleRepository.getAllClients();
    }

    public String validate(com.shibi.app.dto.Role role, boolean isUpdate, Long id){

        Role existingRole = null;
        if (isUpdate){
            if (id == null){
                return "Role id is not provided";
            }

            existingRole = getByNameAndNotId(role.getName(), id);
        }else {
            existingRole = getByName(role.getName());
        }

        if (existingRole != null){
            return "Role name '"+role.getName()+"' already exist";
        }

        return null;
    }

    public Page<Role> getAll(Pageable pageable){
        return roleRepository.findAll(pageable);
    }

//    public RoleResponse generateResponse(Role role, Set<TaskResponse> taskResponseSet){
//        return new RoleResponse(role.getId(),role.getName(),role.getDescription(),role.isActivated(), taskResponseSet);
//    }
}
