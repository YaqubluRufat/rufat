package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.TokenDto;
import com.example.demo.Dto.Tokens;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.IncorrectPassword;
import com.example.demo.Exception.IsEnbaled;
import com.example.demo.Exception.RoleNotFound;
import com.example.demo.Exception.UserAlreadyExists;
import com.example.demo.Repository.RefreshRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public LoginRegistryService(UserRepository userRepository, PasswordEncoder passwordEncoder, MyUserDetailsService myUserDetailsService, JwtService jwtService, RoleRepository roleRepository, RefreshRepository refreshRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.refreshRepository = refreshRepository;
    }

    public Tokens login(LoginDto loginDto) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUsername());
        if (!userDetails.isEnabled()) {
            throw new IsEnbaled("User is disabled");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new IncorrectPassword("Password incorrect");
        }
            String accessToken = jwtService.generateToken(userDetails);
            RefreshToken refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());
            log.info("User in login from username:{}",userDetails.getUsername());
            return new Tokens(accessToken, refreshToken.getToken());

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
            return ResponseEntity.ok("Register");

        }
    }
        public void logout(TokenDto tokenDto){
            RefreshToken refreshToken = refreshRepository.findByToken(tokenDto.getRefreshToken()).orElseThrow(() ->
                    new RuntimeException("Not found"));
            refreshRepository.delete(refreshToken);
            log.info("user log out from user: {}",refreshToken.getUsername());

        }

}
