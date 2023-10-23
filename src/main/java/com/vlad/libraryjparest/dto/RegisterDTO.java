package com.vlad.libraryjparest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private String role;
}
