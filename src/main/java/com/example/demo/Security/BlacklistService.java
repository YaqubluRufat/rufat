package com.example.demo.Security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BlacklistService {

    private final StringRedisTemplate redisTemplate;

    public BlacklistService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

   public void blackList(String token,Date expiration){
       long time = expiration.getTime();
       redisTemplate.opsForValue().set(token,"blacklist",time,TimeUnit.MILLISECONDS);
   }
   public boolean isBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
   }
}
