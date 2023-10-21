package com.vlad.libraryjparest.repository;

import com.vlad.libraryjparest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findUserByUsername(String username);
}
