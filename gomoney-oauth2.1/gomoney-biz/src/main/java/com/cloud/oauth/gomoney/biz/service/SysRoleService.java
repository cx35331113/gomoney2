package com.cloud.oauth.gomoney.biz.service;


import com.cloud.oauth.gomoney.api.entity.sys.SysRole;
import com.cloud.oauth.gomoney.api.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    PageUtils findSysRoleByPage(Map<String, Object> params);

    List<SysRole> findSysRoleAll();

    List<SysRole> findSysRoleByUserId(String id);

    SysRole findSysRoleByRoleId(String id);

    void addSysRole(SysRole sysRole);

    void delSysRoleById(String[] ids);

    void updateSysRole(SysRole sysRole);
}
