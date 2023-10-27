package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY = "fdb6185896c8824c88607f9381038da5b13c211f5a911b821012c0df868599bd";

    @Override
    public String extractUsername(String token) {
        return null;
    }

    @Override
    public String generateToken(User user) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        return false;
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSignInKey(){
        return SECRET_KEY.getBytes();
    }
}
