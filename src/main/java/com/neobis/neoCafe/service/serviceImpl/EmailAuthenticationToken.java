package com.neobis.neoCafe.service.serviceImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class EmailAuthenticationToken implements Authentication {

    private String email;
    private boolean authenticated;
    private final UserDetails userDetails;
    public EmailAuthenticationToken(String email, UserServiceImpl userService) {
        this.userDetails = userService.loadUserByUsername(email); // Получаем информацию о пользователе по email
        this.authenticated = userDetails != null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}
