package com.example.collections_backend.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

public enum Role {
    USER, ADMIN;

    public ArrayList<SimpleGrantedAuthority> getAuthorities(){
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
