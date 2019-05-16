//package com.shibi.app.usermanagement.controller;
//
//import com.shibi.app.dto.Response;
//import com.shibi.app.enums.UserType;
//import com.shibi.app.services.impl.RoleService;
//import com.shibi.app.usermanagement.dto.Role;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
///**
// * Created by User on 14/06/2018.
// */
//@RestController
//@RequestMapping("/role")
//public class RoleController {
//
//    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
//
//
//    private RoleService roleService;
//
//    @Autowired
//    private void setRoleService(RoleService roleService){
//        this.roleService = roleService;
//    }
//
//    @PostMapping(value = "/create-role")
//    public ResponseEntity createRole(@Valid @RequestBody Role role) throws Exception{
//
//        com.shibi.app.dto.Role role1 = new com.shibi.app.dto.Role();
//        String errorResult = roleService.validate(role1,false,null);
//        if(errorResult != null){
//            return new ResponseEntity(new Response("Bad request"), HttpStatus.BAD_REQUEST);
//        }
//
//        com.shibi.app.models.Role newRole = new com.shibi.app.models.Role();
//        newRole.setRoleName(role.getName());
////        newRole.setUserType(role.getUserType());
//        newRole = roleService.save(newRole);
//
//        return new ResponseEntity(new Response("Role is successfully created"), HttpStatus.OK);
//    }
//
//    @ApiIgnore
//    @GetMapping(value = "/all-roles")
//    public ResponseEntity getAllRoles(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
//                                      @Valid @NotNull(message = "page size is required")@RequestParam int pageSize) {
//        return ResponseEntity.ok(roleService.getAll(new PageRequest(pageNumber, pageSize)));
//    }
//
////    @GetMapping("/activated/{userType}")
////    public ResponseEntity getActivatedUserRoles(@PathVariable("userType") UserType userType) {
////        return ResponseEntity.ok(roleService.getActivated(userType));
////    }
//
//    @PutMapping("/updateUserRole")
//    public ResponseEntity updateUserRole(@Valid @RequestBody com.shibi.app.dto.Role role){
//        String errorResult = roleService.validate(role, true,role.getId());
//        if(errorResult != null){
//            return new ResponseEntity(new Response("Bad Request"), HttpStatus.BAD_REQUEST);
//        }
//
//        com.shibi.app.models.Role newRole = roleService.get(role.getId());
//        if(newRole == null){
//            return new ResponseEntity(new Response("role not found"), HttpStatus.BAD_REQUEST);
//        }
//
//        newRole.setRoleName(role.getName());
////        newRole.setUserType(role.getUserType());
//        newRole = roleService.save(newRole);
//
//        return new ResponseEntity(new Response("role is now updated"), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}/toggle")
//    public ResponseEntity toggleRole(@PathVariable Long id) throws Exception {
//
//        return ResponseEntity.ok(roleService.toggle(id));
//
//    }
//}
