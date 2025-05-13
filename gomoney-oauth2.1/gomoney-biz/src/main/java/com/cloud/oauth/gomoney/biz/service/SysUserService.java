package com.cloud.oauth.gomoney.biz.service;


import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.core.utils.R;

import java.util.Map;

public interface SysUserService {
    SysUser authorizedByPassword(String username,String password);

    R createToken(String id);

    SysUser findSysUserByName(String name);

    SysUser login(String name);

    PageUtils list(Map<String, Object> params);

    void updatePassword(SysUser login);

    SysUser findSysUserById(String id);

    void updateSysUser(SysUser user);

    void saveSysUser(SysUser user);

    SysUser findSysUserByToken(String token);

    void delSysUser(String[] ids);

    SysUser findSysUserByMobile(String mobile);
}
