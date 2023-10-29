package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.dto.AuthenticationDTO;
import com.vlad.libraryjparest.dto.RegisterDTO;
import com.vlad.libraryjparest.entity.Role;
import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.exception_handling.security_exception.NoSuchRoleException;
import com.vlad.libraryjparest.exception_handling.security_exception.UserAlreadyExistsException;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;

    public Optional<User> registerUser(RegisterDTO dto) throws UserAlreadyExistsException {
        if(userService.userExists(dto.getUsername()))
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        Optional<Role> role = roleRepository.findByAuthority(dto.getRole());
        if(role.isEmpty())
            throw new NoSuchRoleException(dto.getRole() + " is not a valid role");
        String encoded = encoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), encoded);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role.get());
        user.setAuthorities(roleSet);
        return Optional.of(userService.saveUser(user));
    }

    public Optional<String> loginUser(AuthenticationDTO dto){
        try{
            manager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword()));
            UserDetails user = userService.loadUserByUsername(dto.getUsername());
            String jwt = jwtService.generateToken(user);
            return Optional.of(jwt);
        }
        catch (AuthenticationException e){
            return Optional.empty();
        }
    }

}
