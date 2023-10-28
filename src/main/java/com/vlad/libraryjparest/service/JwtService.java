package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String extractUsername(String token);

    public String generateToken(UserDetails user);

    public boolean isTokenValid(String token, UserDetails user);


}
