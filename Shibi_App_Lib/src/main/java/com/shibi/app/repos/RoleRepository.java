package com.shibi.app.repos;

import com.shibi.app.models.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by User on 06/06/2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


    Page<Role> findAll(Pageable pageable);

    @Query("select r from Role r where r.roleId = :roleId")
    Role findOne(@Param("roleId") Long roleId);

    @Query("select r from Role r where lower(r.name) = :name ")
    Role findByName(@Param("name") String name);

//    @Query("select new com.upl.nibss.bvn.dto.RoleResponse(r.id, r.name, r.description, r.userType, r.activated, r.tasks) from Role r")
//    Page<RoleResponse> getSimpleResponseAll(Pageable pageable);

//    @Query("select r from Role r where r.activated = true")
//    List<Role> getAllActivated();

    @Query("select r from Role r where r.name = :name and r.roleId <> :roleId")
    Role getByNameAndNotId(@Param("name") String name, @Param("roleId") Long roleId);


//    @Modifying
//    @Query("UPDATE Role r SET r.activated = CASE r.activated WHEN true THEN false ELSE true END WHERE r.roleId = :roleId")
//    int toggle(@Param("roleId") Long roleId);

    @Query(value = "select * from Role  where roleId = 3", nativeQuery = true)
    List<Role> getAllClients();


}
