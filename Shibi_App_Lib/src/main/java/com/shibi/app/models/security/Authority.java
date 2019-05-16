package com.shibi.app.models.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by User on 26/06/2018.
 */
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 123123L;

    private final String authority;

    public Authority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority(){
        return authority;
    }
}
