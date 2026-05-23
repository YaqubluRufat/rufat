package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.Tokens;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoginRegistryController {


    private final LoginRegistryService loginRegistryService;

    public LoginRegistryController(LoginRegistryService loginRegistryService) {
        this.loginRegistryService = loginRegistryService;
    }
    @PostMapping("/login")
    public ResponseEntity<Tokens>login(@RequestBody LoginDto loginDto){
        Tokens login = loginRegistryService.login(loginDto);
        return ResponseEntity.ok(login);
    }
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterDto registerDto){
        ResponseEntity<?> register = loginRegistryService.register(registerDto);
        return ResponseEntity.ok(register);
    }
}
