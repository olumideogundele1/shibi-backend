package com.shibi.app.models.security;

import com.shibi.app.models.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 27/06/2018.
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 890345L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;

    public UserRole(){

    }
    public UserRole(User user, Role role){
        this.user = user;
        this.role = role;

    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
