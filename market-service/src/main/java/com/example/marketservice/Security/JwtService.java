package com.example.marketservice.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(SecretKey key) {
        this.key = key;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractByUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());

        }
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token)
                .get("userId", Long.class);

    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token)
                .get("roles", List.class);
    }

    public Date extractByDate(String token) {
        return extractAllClaims(token)
                .getExpiration();
    }

    public boolean isExpired(String token) {
        if (extractByDate(token).before(new Date())) {
            return true;
        }
        return false;
    }

    public boolean validation(String token) {
        try {
            extractByUsername(token);
            return !isExpired(token);
        } catch (Exception e) {
            return false;
        }


    }
}

