package com.cloud.oauth.gomoney.security.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.feign.RemoteUserService;
import com.cloud.oauth.gomoney.core.constant.SecurityConstants;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.security.service.GoUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

/**
 * @Description //TODO
 * @Author chenx
 * 我们自定义用户名和密码登录和数据库结合
 * @Date 2021/4/25 16:00
 **/
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements GoUserDetailsService {

    //后台用户数据库结合
    private final RemoteUserService remoteUserService;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    //和数据库结合的真实登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //后台
        SysUser user = remoteUserService.info(username);
        if (user != null && user.getState() == 1) {
            return getUserDetails(user);
        } else {
            return null;

        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
