package com.vlad.libraryjparest.security;

import lombok.Getter;
import lombok.Setter;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    @Getter
    private final String role;

    private Roles(String role){
        this.role = role;
    }
}
