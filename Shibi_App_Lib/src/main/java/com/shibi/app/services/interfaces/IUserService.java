package com.shibi.app.services.interfaces;

import com.shibi.app.models.security.Role;
import com.shibi.app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by User on 06/06/2018.
 */
public interface IUserService {

    User generateUser ( User user, Set<Role> roles) throws Exception;

    String generatePassword() throws Exception;

    String generateUsername(String email) throws Exception;

    Optional<User> findByUsername(String username);

    User findByEmail(String email );

    Page<User> findAll(PageRequest pageRequest);

    User save(User user) throws Exception;

    boolean toggle(Long id) throws Exception;

    List<User> getAll() throws Exception;

    User get(Long id) throws Exception;

    void sendRecoveryEmail(String email, String link);

   List <User> getActivatedUsers(Long id) throws Exception;
}
