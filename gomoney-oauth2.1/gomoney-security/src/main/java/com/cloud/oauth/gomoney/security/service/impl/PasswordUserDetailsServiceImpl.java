package com.cloud.oauth.gomoney.security.service.impl;

import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.feign.RemoteUserService;
import com.cloud.oauth.gomoney.security.model.SysUserDetail;
import com.cloud.oauth.gomoney.security.service.PasswordUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class PasswordUserDetailsServiceImpl implements PasswordUserDetailsService {

    private final RemoteUserService remoteUserService;

    public UserDetails loadUserByUserName(String username, String password) throws UsernameNotFoundException {
        SysUser sysUser = remoteUserService.authorized(username, password);
        return new SysUserDetail(sysUser);
    }
}
