package com.cloud.oauth.gomoney.workflow.controller;

import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.feign.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {

    @Autowired
    private RemoteUserService userService;

    protected SysUser getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysUser sysUser = userService.info(username);
        return sysUser;
    }
}
