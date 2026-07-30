package com.example.demo.Security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.text.NumberFormat.Field.PREFIX;

@Component
public class Blacklist {
    private final RedisTemplate<String, String> redisTemplate;

    public Blacklist(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToBlacklist(String token, Date expiration){
        long time = expiration.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(PREFIX+token,"blacklist",time, TimeUnit.MILLISECONDS);
    }
    public boolean isBlackListed(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX+token));
    }
}
