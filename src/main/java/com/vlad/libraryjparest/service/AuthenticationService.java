package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.exception_handling.user_exception.UserAlreadyExistsException;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User registerUser(String username, String password, ) throws UserAlreadyExistsException {
        if(userRepository.findUserByUsername(username).isPresent())
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        String encoded = encoder.encode(password);

    }

}
