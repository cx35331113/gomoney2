package com.cloud.oauth.gomoney.gateway.common.auth;

import cn.hutool.core.util.StrUtil;
import com.cloud.oauth.gomoney.gateway.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Service
public class AuthService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 获取所有的接口url与用户权限的映射关系,格式仿造了shiro的权限配置格式
     */
    public Map<String, String> getAllUrlPermissionsMap() {
        Map<String, String> urlPermissionsMap = new HashMap();
        urlPermissionsMap.put("/gomoney-auth/sys/authentication/**", "anon");
        urlPermissionsMap.put("/gomoney-auth/oauth/token", "anon");
        urlPermissionsMap.put("/gomoney-auth/t/user/**", "anon");
        urlPermissionsMap.put("/gomoney-auth/v2/api-docs", "anon");
        urlPermissionsMap.put("/gomoney-auth/v3/api-docs", "anon");
        urlPermissionsMap.put("/gomoney-biz/v2/api-docs", "anon");
        urlPermissionsMap.put("/gomoney-biz/v3/api-docs", "anon");
        urlPermissionsMap.put("/gomoney-file/**", "anon");
        urlPermissionsMap.put("/gomoney-file/v2/api-docs", "anon");
        urlPermissionsMap.put("/gomoney-file/v3/api-docs", "anon");
        urlPermissionsMap.put("/endorsement/v2/api-docs", "anon");
        urlPermissionsMap.put("/endorsement/v3/api-docs", "anon");
        return urlPermissionsMap;
    }

    /**
     * 根据一个确定url获取该url对应的权限设置
     * 利用AntPathMatcher进行模式匹配
     */
    private String getUrlPermission(String url) {
        if (StrUtil.isBlank(url)) {
            return null;
        }
        Map<String, String> urlPermissionsMap = getAllUrlPermissionsMap();
        Set<String> urlPatterns = urlPermissionsMap.keySet();
        for (String pattern : urlPatterns) {
            boolean match = false;
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, url);
            } else {
                match = url.equals(pattern);
            }
            if (match) {
                return urlPermissionsMap.get(pattern);
            }
        }
        return null;
    }

    //@Autowired
    private RedisUtil redisUtil;

    public boolean verifyToken(String url, String token) {
        String urlPermission = getUrlPermission(url);
        if ("anon".equals(urlPermission)) {
            return true;
        } else if (StrUtil.isBlank(token) || redisUtil.get(token) == null) {
            return false;
        } else {
            ArrayList list = (ArrayList) redisUtil.get(redisUtil.get(token).toString());
            if (null != list && list.get(0).equals("com.gomoney.common.entity.sys.SysUser")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
