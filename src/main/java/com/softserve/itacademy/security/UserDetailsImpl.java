package com.softserve.itacademy.security;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl //implements UserDetails , GrantedAuthority
{
    private static final boolean isActive = true;

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

//    @Override
//    public String getAuthority() {
//        return user.getRole().getName().toUpperCase();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(getAuthority()));
//        return grantedAuthorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return isActive;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isActive;
//    }

}
