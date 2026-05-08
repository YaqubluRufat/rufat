package com.example.demo.Dto;

import com.example.demo.Security.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;

}
