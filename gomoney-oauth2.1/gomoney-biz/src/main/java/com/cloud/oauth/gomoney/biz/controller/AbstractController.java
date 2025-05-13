package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.biz.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserServiceImpl userService;

    protected SysUser getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysUser sysUser = userService.findSysUserByName(username);
        return sysUser;
    }
}
