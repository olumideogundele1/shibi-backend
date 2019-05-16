package com.shibi.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shibi.app.models.security.Authority;
import com.shibi.app.models.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by User on 06/06/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User extends SuperModel implements UserDetails, Serializable {

    private static final long serialVersionUID = 902783495L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id",nullable = false)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String sessionId;

    // @Transient

    @Column(unique = true)
    private String email;
    private String phone;
    private boolean enabled = true;
    private boolean loggedIn;
    private Timestamp lastLogIn;

    @JsonIgnore
    protected boolean deleted = false;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();


    @OneToOne(mappedBy = "user")
    private Stores stores;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            Set<GrantedAuthority> authorities = new HashSet<>();
            userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
            return authorities;
        }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),username, password, loggedIn, lastLogIn,enabled);
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() != object.getClass()){
            return false;
        }
        if(!super.equals(object)){
            return false;
        }
        User user = (User) object;
        return loggedIn == user.loggedIn &&
                enabled == user.enabled &&
                Objects.equals(username,user.username) &&
                Objects.equals(password,user.password) &&
                Objects.equals(lastLogIn,user.lastLogIn);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", loggedIn=").append(loggedIn);
        sb.append(", lastLogIn=").append(lastLogIn);
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }

}
