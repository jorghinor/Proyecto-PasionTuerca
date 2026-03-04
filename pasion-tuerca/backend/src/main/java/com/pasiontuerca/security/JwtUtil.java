package com.pasiontuerca.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration-minutes}")
    private int expirationMinutes;

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        long now = System.currentTimeMillis();
        Date exp = new Date(now + expirationMinutes * 60L * 1000L);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(exp)
                .setIssuedAt(new Date(now))
                .signWith(key)
                .compact();
    }

    public String validateAndGetUsername(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String subject = claims.getSubject();
            if (subject != null) {
                return subject;
            }
            // Fallback for old tokens: try to get username from email claim
            return claims.get("email", String.class);
        } catch (JwtException ex) {
            return null;
        }
    }
}
