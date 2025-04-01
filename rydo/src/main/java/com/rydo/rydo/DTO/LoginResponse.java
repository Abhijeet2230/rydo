package com.rydo.rydo.DTO;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class LoginResponse {
    private String message;
    private boolean success;
    private String role;
    private UserDetails user; // Add user details

    public LoginResponse(String role, boolean success, UserDetails user) {
        this.role = role;
        this.success = success;
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user != null ? user.getAuthorities() : null;
    }
}
