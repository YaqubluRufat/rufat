package com.example.demo.Security;

import com.example.demo.DTO.LoginDto;
import com.example.demo.Exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoginController {
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginController(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?>login (@RequestBody LoginDto loginDto){
        try{
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUsername());
            boolean matches = passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());

            if(matches){
                String token = jwtService.generateToken(userDetails);
                return ResponseEntity.ok(token);

            }else {
                return ResponseEntity.ok("Sifre yalnisdir");

            }
        }catch (UserNotFoundException ex){
            return ResponseEntity.ok("User not found");
        }


    }
}
