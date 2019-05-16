package com.shibi.app.repos;


import com.shibi.app.models.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by User on 27/06/2018.
 */
@Transactional
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select u from UserRole u where u.user.id = :userId")
    UserRole getByUserId(@Param("userId") Long userId);
}
