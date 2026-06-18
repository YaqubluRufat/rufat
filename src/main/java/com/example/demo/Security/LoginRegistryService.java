package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.TokenDto;
import com.example.demo.Dto.Tokens;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.*;
import com.example.demo.Repository.RefreshRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceKafka.User.UserProducerEvent;
import com.example.demo.ServiceKafka.User.UserSavedEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LoginRegistryService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final RefreshRepository refreshRepository;
    private final BlacklistService blacklistService;
    private final UserProducerEvent userProducerEvent;

    public LoginRegistryService(UserRepository userRepository, PasswordEncoder passwordEncoder, MyUserDetailsService myUserDetailsService, JwtService jwtService, RoleRepository roleRepository, RefreshRepository refreshRepository, BlacklistService blacklistService, UserProducerEvent userProducerEvent) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.refreshRepository = refreshRepository;
        this.blacklistService = blacklistService;
        this.userProducerEvent = userProducerEvent;
    }

    public TokenDto login(LoginDto loginDto) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUsername());
        if (!userDetails.isEnabled()) {
            throw new IsEnbaled("User is disabled");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new IncorrectPassword("Password incorrect");
        }
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());
        log.info("User in login from username:{}", userDetails.getUsername());

        return new TokenDto(accessToken, refreshToken.getToken());

    }

    public ResponseEntity<?> register(RegisterDto registerDto) {
        Optional<User> byUsername = userRepository.findByUsername(registerDto.getUsername());
        if (byUsername.isPresent()) {
            throw new UserAlreadyExists("User already exists");

        } else {
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            Role role = roleRepository.findByName("USER").orElseThrow(() ->
                    new RoleNotFound("Role not found"));
            user.getRoles().add(role);
            userRepository.save(user);
            UserSavedEvent userSavedEvent = new UserSavedEvent(user.getId(), user.getUsername(), user.getPassword(), LocalDateTime.now());
            userProducerEvent.onPublishSavedUser(userSavedEvent);
            return ResponseEntity.ok("Register");

        }
    }

    public void logout(TokenDto tokenDto) {
        RefreshToken refreshToken = refreshRepository.findByToken(tokenDto.getRefreshToken()).orElseThrow(() -> new RefreshTokenNotFoundException("Refresh not found"));
        Date date = jwtService.extractByExpirationDate(tokenDto.getAccessToken());
        blacklistService.blackList(tokenDto.getAccessToken(), date);
        refreshRepository.delete(refreshToken);
    }

    public String token(TokenDto tokenDto) {


        UserDetails userDetails = myUserDetailsService.loadUserByUsername(tokenDto.getRefreshToken());
        if (jwtService.validation(tokenDto.getRefreshToken(), userDetails)) {
            String generateToken = jwtService.generateToken(userDetails);
            return generateToken;
        }
        throw new RuntimeException("Token invalid");


    }


}
