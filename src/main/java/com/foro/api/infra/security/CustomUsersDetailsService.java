package com.foro.api.infra.security;

import com.foro.api.domain.role.Role;
import com.foro.api.domain.user.User;
import com.foro.api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    @Autowired
    public CustomUsersDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> getToAuthorities(Role roles) {
        return Collections.singleton(new SimpleGrantedAuthority(roles.getNameRole()));
    }
}
