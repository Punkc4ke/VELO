package com.example.VELO.models;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER, ADMIN, COMMENT_MODERATOR;

    //Поля

    @Override
    public String getAuthority() {
        return name();
    }
}
