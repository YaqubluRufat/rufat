package com.example.demo.Security;

import com.example.demo.Dto.RefreshTokenDtoIMPL;
import com.example.demo.Dto.Tokens;
import com.example.demo.Exception.BadCredentialsException;
import com.example.demo.Repository.RefreshTokenRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService, MyUserDetailsService myUserDetailsService, RedisTemplate<String, Object> redisTemplate) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
        this.redisTemplate = redisTemplate;
    }


    public void logOut( RefreshTokenDtoIMPL dto) {


        refreshTokenRepository.findByToken(dto.getRefreshToken())
                .ifPresent(refreshTokenRepository::delete);

        String accessToken = dto.getAccessToken().trim();



        String redisKey = "BL_" + accessToken;

        redisTemplate.opsForValue().set(
                redisKey,
                "logout",
                Duration.ofMinutes(15)
        );
    }
}


