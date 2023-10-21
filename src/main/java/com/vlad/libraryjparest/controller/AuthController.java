package com.vlad.libraryjparest.controller;

import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        if(userRepository.findUserByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

    }

}
