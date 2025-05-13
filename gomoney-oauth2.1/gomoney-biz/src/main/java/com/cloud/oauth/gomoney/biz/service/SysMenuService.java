package com.cloud.oauth.gomoney.biz.service;


import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.core.utils.R;

import java.util.List;
import java.util.Set;

public interface SysMenuService {
    List<SysMenu> findMenus();

    List<SysMenu> nav(SysUser user);

    Set<String> permissions(SysUser user);

    Set<String> getAllPermissions(List<SysMenu> menus);

    Set<String> getUserPermissions(String id);

    SysMenu findMenuById(String id);

    void addMenu(SysMenu m);

    List<SysMenu> findMenuForAll(Integer id);

    List<SysMenu> findChildMenuById(String id);

    List<SysMenu> findPermissions(List<SysMenu> ms);

    List<SysMenu> findChild(SysMenu menu, List<SysMenu> menus);

    List<SysMenu> findMenusByUserId(String id);

    void updateMenu(SysMenu m);

    void delMenu(String id);

    List<SysMenu> selectMenus();
}
