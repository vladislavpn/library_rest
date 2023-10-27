package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;

public interface JwtService {

    public String extractUsername(String token);

    public String generateToken(User user);

    public boolean isTokenValid(String token, User user);


}
