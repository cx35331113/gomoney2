package com.cloud.oauth.gomoney.security.service;


import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description //TODO
 * @Author chenx
 * 我们自定义用户名和密码登录和数据库结合
 * @Date 2021/4/25 16:00
 **/
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements GoUserDetailsService {


    private final RemoteUserService remoteUserService;
    //和数据库结合的真实登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //后台
        SysUser user = remoteUserService.info(username);
        return getUserDetails(user);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
