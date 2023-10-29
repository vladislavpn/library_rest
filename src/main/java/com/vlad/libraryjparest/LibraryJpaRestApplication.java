package com.vlad.libraryjparest;

import com.vlad.libraryjparest.entity.Role;
import com.vlad.libraryjparest.entity.User;
import com.vlad.libraryjparest.repository.RoleRepository;
import com.vlad.libraryjparest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LibraryJpaRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryJpaRestApplication.class, args);
    }

    @Profile("!test")
    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder){
        return args -> {
            if(userRepository.findUserByUsername("admin").isPresent()) return;
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByAuthority("ADMIN").get());
            User user = new User("admin", encoder.encode("admin"));
            user.setAuthorities(roles);
            userRepository.save(user);
        };
    }
}
