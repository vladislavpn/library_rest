package com.vlad.libraryjparest.repository;

import com.vlad.libraryjparest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findByAuthority(String authority);
}
