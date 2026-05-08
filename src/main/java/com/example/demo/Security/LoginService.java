package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.Tokens;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.BadCredentialsException;
import com.example.demo.Exception.UserAlreadyExists;
import com.example.demo.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginService {
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final UserRepository userRepository;

    public LoginService(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository userRepository) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Tokens login(@RequestBody@Valid LoginDto loginDto){

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUsername());
        if(!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())){
            throw new BadCredentialsException("username or password incorrect");
            }
            String token = jwtService.generateAccessToken(userDetails);
            RefreshToken refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

            return new Tokens(token,refreshToken.getToken());
        }

    public User register (@RequestBody@Valid RegisterDto registerDto){
        Optional<User> byUsername = userRepository.findByUsername(registerDto.getUsername());
        if(byUsername.isPresent()){
            throw new UserAlreadyExists("User already exists");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }


}

