package com.example.demo.Security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretKey {
@Value("${JWT_SECRET}")
    private String secret;

@Bean
    public javax.crypto.SecretKey key(){
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
}


}
