package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    @Override
    public String extractUsername(String token) {
         return extractAllClaims(token).getPayload()
                 .getSubject();
    }

    private Jws<Claims> extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token);
    }

    @Override
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && isTokenNonExpired(token));
    }

    private boolean isTokenNonExpired(String token){
        return extractAllClaims(token).getPayload()
                .getExpiration().before(new Date());
    }
}
