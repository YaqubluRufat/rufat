package com.example.demo.Security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfig {


        @Value("${JWT_SECRET}")
        private String secret;

        @Bean
        public SecretKey jwtKey() {
            return Keys.hmacShaKeyFor(
                    Decoders.BASE64.decode(secret)
            );
        }
    }





