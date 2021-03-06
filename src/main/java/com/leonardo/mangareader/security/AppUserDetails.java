package com.leonardo.mangareader.security;

import java.util.Collection;
import java.util.HashSet;

import com.leonardo.mangareader.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class AppUserDetails implements UserDetails{

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Essa aplicação não irá utilizar Roles ou Authorities
        return new HashSet<SimpleGrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true;
    }
    
}
