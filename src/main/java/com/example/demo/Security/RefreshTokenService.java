package com.example.demo.Security;

import com.example.demo.Dto.RefreshTokenDtoIMPL;

import com.example.demo.Repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;




@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;



    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository ) {
        this.refreshTokenRepository = refreshTokenRepository;

    }


    public void logOut( RefreshTokenDtoIMPL dto) {


        refreshTokenRepository.findByToken(dto.getRefreshToken())
                .ifPresent(refreshTokenRepository::delete);

    }
}


