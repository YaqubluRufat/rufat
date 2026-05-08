package com.example.demo.Security;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RefreshTokenDtoIMPL;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.Tokens;
import com.example.demo.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    private final LoginService loginService;


    public LoginController(LoginService loginService) {
        this.loginService = loginService;

    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginDto loginDto){
        Tokens login = loginService.login(loginDto);
        return ResponseEntity.ok(login);
    }
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterDto registerDto){
        User register = loginService.register(registerDto);
        return ResponseEntity.ok(register);
    }

}
