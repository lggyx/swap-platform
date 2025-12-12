package com.lggyx.utils;


import com.lggyx.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public String generateToken(String account) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getTtl()))
                .signWith(secretKey)
                .compact();
    }

    public String getAccountFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isExpired(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    //删除token
    public void deleteToken(String token) {
        Jwts.builder()
                .setSubject(null)
                .setExpiration(new Date())
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                .compact();
    }


}