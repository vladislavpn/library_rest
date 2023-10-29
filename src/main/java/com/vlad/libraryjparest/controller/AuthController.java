package com.vlad.libraryjparest.controller;

import com.vlad.libraryjparest.dto.AuthenticationDTO;
import com.vlad.libraryjparest.dto.RegisterDTO;
import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import com.vlad.libraryjparest.service.AuthenticationService;
import com.vlad.libraryjparest.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO dto){
        Optional<User> registered = service.registerUser(dto);
        if(registered.isPresent())
            return ResponseEntity.ok("User registered");
        else return new ResponseEntity<>("An error occurred during registration", HttpStatus.CONFLICT);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationDTO dto){
        Optional<String> jwt =  service.loginUser(dto);
        return jwt.map(ResponseEntity::ok).orElseGet(() ->
                new ResponseEntity<>("An error occurred during authentication", HttpStatus.CONFLICT));
    }

}
