package com.vlad.libraryjparest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDTO {
    String username;
    String password;
}
