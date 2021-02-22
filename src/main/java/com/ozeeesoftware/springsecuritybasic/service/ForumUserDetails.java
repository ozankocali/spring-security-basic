package com.ozeeesoftware.springsecuritybasic.service;

import com.ozeeesoftware.springsecuritybasic.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ForumUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public ForumUserDetails(User user) {

        this.userName=user.getUserName();
        this.password=user.getPassword();
        this.isActive=user.isActive();
        this.authorities= Arrays.stream(user.getUserRoles().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return isActive;
    }
}
