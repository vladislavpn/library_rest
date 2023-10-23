package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.dto.RegisterDTO;
import com.vlad.libraryjparest.entity.Role;
import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.exception_handling.security_exception.NoSuchRoleException;
import com.vlad.libraryjparest.exception_handling.security_exception.UserAlreadyExistsException;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Optional<User> registerUser(RegisterDTO dto) throws UserAlreadyExistsException {
        if(userRepository.findUserByUsername(dto.getUsername()).isPresent())
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        Optional<Role> role = roleRepository.findByAuthority(dto.getRole());
        if(role.isEmpty())
            throw new NoSuchRoleException(dto.getRole() + " is not a valid role");
        String encoded = encoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), encoded);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role.get());
        user.setAuthorities(roleSet);
        return Optional.of(userRepository.save(user));
    }

}
