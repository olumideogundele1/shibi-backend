package com.shibi.app.repos;

import com.shibi.app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by User on 06/06/2018.
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled = CASE u.enabled WHEN true THEN false ELSE true END WHERE u.id = :id")
    int toggle(@Param("id") Long id);

//    @Query("select count(u) from User u where u.userType = ?1")
//    long countByUserType(UserType userType);

    @Query("select u from User u where u.enabled = true")
    List<User> findAllEnabled();


    @Query("select u from User u where u.id = :id")
    User findOne(@Param("id") Long id);

    @Query("select u from User u where u.id = :id and u.enabled = true")
    User getByUser(@Param("id") Long id);

    Page<User> findAll(Pageable pageable);

//    @Modifying
//    @Query("update User u set u.loggedIn = false , u.updatedAt = current_date where u.id = :userId")
//    void logOutUser(@Param("userId") Long userId);

    @Query(value = "select * from users u where (u.createdAt between :start and :stop) and (u.phone_number like :param or u.username like :param or u.firstName like :param or u.lastName like :param) \n #pageable \n",
            countQuery = "SELECT count(u.id) from users u where (u.createdAt between :start and :stop) and (u.phone_number like :param or u.username like :param or u.firstName like :param or u.lastName like :param)", nativeQuery = true)
    Page<User> findAllBySearch(@Param("start") String from, @Param("stop") String to, @Param("param") String param , Pageable pageable);

    @Query(value = "select * from users u where u.createdAt between :start and :stop \n #pageable \n", countQuery = "SELECT count(u.id) from users u where u.createdAt between :start and :stop", nativeQuery = true)
    Page<User> findAllBySearch(@Param("start") String from, @Param("stop") String to, Pageable pageable);

//    @Query("select u from User u where u.email = :email and u.userType = :userType")
//    User findUserByEmailAddressAndUserType(@Param("email") String email, @Param("userType") UserType userType);

//    @Query("select u from User u where u.email = :email and u.userType in :userType")
//    User findUserByEmailAddressAndUserType(@Param("email") String email, @Param("userType") List<UserType> userType);
//
//    @Query("select u from User u where u.username = :username and u.userType in :userType")
//    User findUserByUsernameAndUserType(@Param("username") String username, @Param("userType") List<UserType> userType);

    @Query("select u from User u where u.email = :email and u.id <> :id")
    User findUserByEmailAddressAndNotId(@Param("email") String emailAddress, @Param("id") Long id);

//    @Query("select u from User u where u.role_id= :roleId and u.enabled=true and u.deleted = false")
//    Set<Role> findUserByRoleId(@Param("roleId") Long roleId);

    @Query("select u.username from User u where u.id = :id")
    String findUsernameById(@Param("id") Long id);

    Optional<User> findByUsername(String username);
    User findByEmail(String email);

//    @Query("select u.id from User u where u.role.id = ?2")
//    Long findAdminUsers(long id);
//
//    @Query("select u.id from User u where u.role.id = ?3")
//    Long findClientUsers(long id);
//
//    @Query("select u.id from User u where u.role.id = ?1 ")
//    Long findSuperAdmin(long id);

    @Query("select u from User u where u.id= :id and u.enabled=true")
    List<User> getByActivatedUser(@Param("id") Long id);
}
