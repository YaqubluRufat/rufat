package com.example.demo.Security;

import com.example.demo.Repository.RefreshRepository;
import com.example.demo.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import io.jsonwebtoken.Jwts;
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
    private final SecretKey key;
    private final RefreshRepository refreshRepository;
    private static final long TIME =1000*60*60;

    public JwtService(SecretKey key, RefreshRepository refreshRepository) {
        this.key = key;
        this.refreshRepository = refreshRepository;

    }


    public String generateToken(UserDetails userDetails) {
        List<String> list = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(key)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TIME))
                .claim("roles", list)
                .compact();


    }

    public RefreshToken generateRefreshToken(String username) {
        refreshRepository.markAllByUsername(username);
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(token);
        refreshToken.setExpired(false);
        refreshToken.setUsed(false);
        refreshToken.setExpiratyDate(LocalDateTime.now().plusDays(60));
        return refreshRepository.save(refreshToken);

    }

    public String extractByUsername(String token) {
        try {


        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().
                getSubject();
    }catch (Exception ex) {
            throw new RuntimeException("TOKEN invalid" + ex.getMessage());
        }
        }

    public Date extractByDate(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public boolean isExpired(String token) {
        return  extractByDate(token).before(new Date());


    }

    public boolean validation(String token, UserDetails userDetails) {
        String username = extractByUsername(token);
        return userDetails.getUsername().equals(username) && !isExpired(token);



    }
}
