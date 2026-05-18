package com.example.demo.Security;

import com.example.demo.Repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.expiration}")
    private long expiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecretKey key;


    public JwtService(RefreshTokenRepository refreshTokenRepository, SecretKey key) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.key = key;

    }

    public String generateAccessToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(key)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("roles", roles)
                .compact();
    }

    public RefreshToken generateRefreshToken(String username) {
        refreshTokenRepository.markAllByUsername(username);
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(token);
        refreshToken.setExpired(false);
        refreshToken.setUsed(false);
        refreshToken.setExpirityDate(LocalDateTime.now().plusDays(30));
        return refreshTokenRepository.save(refreshToken);

    }

    public String extractByUsername(String token) {
        return Jwts.parser()
                .verifyWith( key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Date extractByExpirityDate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public boolean isExpired(String token) {
        return (extractByExpirityDate(token).before(new Date()));


    }
      public boolean validation(String token,UserDetails userDetails){
        String username = extractByUsername(token);
        if(userDetails.getUsername().equals(username) && !isExpired(token)){
            return true;
        }return false;
    }
}
