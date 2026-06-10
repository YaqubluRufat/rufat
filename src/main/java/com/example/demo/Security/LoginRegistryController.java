package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.TokenDto;
import com.example.demo.Dto.Tokens;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<TokenDto>login(@RequestBody LoginDto loginDto){
        TokenDto login1 = loginRegistryService.login(loginDto);

        return ResponseEntity.ok(login1);
    }
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterDto registerDto){
        ResponseEntity<?> register = loginRegistryService.register(registerDto);
        return ResponseEntity.ok(register);
    }
    @PostMapping("/out")
    public ResponseEntity<?>logout(@RequestBody TokenDto dto){
        loginRegistryService.logout(dto);
        return ResponseEntity.ok("LOGOUT");
    }
}
