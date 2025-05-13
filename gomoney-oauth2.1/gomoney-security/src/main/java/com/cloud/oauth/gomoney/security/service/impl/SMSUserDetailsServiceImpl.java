package com.cloud.oauth.gomoney.security.service.impl;

import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.feign.RemoteUserService;
import com.cloud.oauth.gomoney.security.model.SysUserDetail;
import com.cloud.oauth.gomoney.security.service.SMSUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class SMSUserDetailsServiceImpl implements SMSUserDetailsService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean checkMobileAndCode(String mobile, String code) throws UsernameNotFoundException {
        if(redisTemplate.opsForValue().get(mobile).equals(code)){
            redisTemplate.delete(mobile);
            return true;
        }
        return false;
    }
}
