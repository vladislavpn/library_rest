package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findUserByUsername(username);
        System.out.println(user.get().getAuthorities());
        return user.orElseThrow(() -> new UsernameNotFoundException("No user with username:" + username));
    }
}
