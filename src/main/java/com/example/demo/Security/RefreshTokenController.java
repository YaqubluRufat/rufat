package com.example.demo.Security;

import com.example.demo.Dto.RefreshTokenDtoIMPL;
import com.example.demo.Dto.Tokens;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

  @PostMapping("/out")
    public void logout(@RequestBody RefreshTokenDtoIMPL refreshTokenDtoIMPL){
        refreshTokenService.logOut(refreshTokenDtoIMPL);

    }

}
