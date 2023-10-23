package com.vlad.libraryjparest.controller;

import com.vlad.libraryjparest.dto.RegisterDTO;
import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import com.vlad.libraryjparest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO dto){
        Optional<User> registered = service.registerUser(dto);
        if(registered.isPresent())
            return new ResponseEntity<>("User registered", HttpStatus.OK);
        else return new ResponseEntity<>("An erorr occured", HttpStatus.CONFLICT);
    }

}
