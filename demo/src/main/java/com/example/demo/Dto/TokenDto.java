package com.example.demo.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;



}
