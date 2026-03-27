package com.example.demo.Security;

import com.example.demo.Repository.RefreshRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {
    private final RefreshRepository refreshRepository;


    private final String SECRET_KEY="YWJvdXRkaXZpZGVnb29zZWRhcmtuZXNzYmFybnVtZXJhbGFyZWxvY2F0aW9ucGlua2Q=";
    private final long VALIDATION_USER_TIME=1000*60*60;
    private final long VALIDATION_USER_DAYS=30;

    public JwtService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }


    public String generateToken(UserDetails userDetails){
        List<String> list = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(getSignkey(SECRET_KEY))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+VALIDATION_USER_TIME))
                .claim("roles",list)
                .compact();
    }
    public SecretKey getSignkey( String secretkey){
        byte[] decode = Decoders.BASE64.decode(secretkey);
        SecretKey secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;

    }
    public RefreshToken generateRefreshToken(String username){
        refreshRepository.markAllIsUsedByUsername(username);
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUsername(username);
        refreshToken.setExpired(false);
        refreshToken.setUsed(false);
        refreshToken.setExpirityDate(LocalDateTime.now().plusDays(VALIDATION_USER_DAYS));
         return refreshRepository.save(refreshToken);
    }
    public String generateUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSignkey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public Date generateExpirationFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSignkey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
    public boolean isExpired(String token){
        if(generateExpirationFromToken(token).before(new Date())){
            return true;

        }return false;
    }
    public boolean validationToken(String token,UserDetails userDetails){
        String username = generateUsernameFromToken(token);
        if(userDetails.getUsername().equals(username)&& !isExpired(token)){
            return true;

        }return false;
    }
}
