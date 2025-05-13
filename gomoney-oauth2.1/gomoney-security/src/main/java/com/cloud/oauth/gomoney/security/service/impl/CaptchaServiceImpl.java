package com.cloud.oauth.gomoney.security.service.impl;

import com.cloud.oauth.gomoney.security.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean verifyCaptcha(String uuid, String rawCode) {
        if(redisTemplate.opsForValue().get(uuid).toString().equalsIgnoreCase(rawCode)){
            redisTemplate.delete(uuid);
            return true;
        }
        return false;
    }
}
