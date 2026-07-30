package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.TokenDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.IncorrectPassword;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.RefreshRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class RegisterService {
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshRepository refreshRepository;
    private final Blacklist blacklist;
    private final UserMapper userMapper;

    public RegisterService(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository userRepository, RoleRepository roleRepository, RefreshRepository refreshRepository, Blacklist blacklist, UserMapper userMapper) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshRepository = refreshRepository;
        this.blacklist = blacklist;

        this.userMapper = userMapper;
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        if (user.isAccountLocked() && user.getLockedUntil() != null && user.getLockedUntil().isBefore(LocalDateTime.now())) {
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLockedUntil(null);
            userRepository.save(user);

        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUsername());
        if (!userDetails.isEnabled()) {

            throw new RuntimeException("User disabled");
        }
        if (!userDetails.isAccountNonLocked()) {
            if (user.getLockedUntil() != null) {
                throw new RuntimeException("User locked");
            }
            throw new RuntimeException("Accaunt blocked");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new RuntimeException("Hesabin vaxdi bitib");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountLocked(true);
                user.setLockedUntil(LocalDateTime.now().plusMinutes(30));
                userRepository.save(user);
                throw new IncorrectPassword("5 defe sef giris edilde");
            }
            userRepository.save(user);
            throw new IncorrectPassword("Password sefdi");
        }
        if (user.getFailedLoginAttempts() > 0 || user.getLockedUntil() != null) {

            user.setAccountLocked(false);
            user.setLockedUntil(null);
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
        String generateToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        return new TokenDto(generateToken, refreshToken.getToken());

    }


    public UserDtoIMPL register(LoginDto loginDto) {
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("User exists");
        }
        User user = new User();
        user.setUsername(loginDto.getUsername());
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        User save = userRepository.save(user);
        return userMapper.toDtoIMPL(save);


    }

    public void logout(TokenDto tokenDto) {

        RefreshToken refreshToken = refreshRepository.findByToken(tokenDto.getRefreshToken()).orElseThrow(() ->
                new RuntimeException("Refresh not found"));

        refreshToken.setUsed(true);
        refreshToken.setExpired(true);
        refreshRepository.save(refreshToken);
        Date date = jwtService.extractByDate(tokenDto.getAccessToken());
        blacklist.addToBlacklist(tokenDto.getAccessToken(),date);

    }
    public String refreshToken(TokenDto tokenDto){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtService.extractByUsername(tokenDto.getRefreshToken()));
        if(jwtService.validation(tokenDto.getRefreshToken(),userDetails)){
           return jwtService.generateToken(userDetails);
        }
        throw new RuntimeException("Invalid token");

    }

}
