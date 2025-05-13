package com.cloud.oauth.gomoney.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.oauth.gomoney.api.entity.sys.SysRole;
import com.cloud.oauth.gomoney.api.entity.sys.SysRoleMenu;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.mapper.SysRoleMapper;
import com.cloud.oauth.gomoney.biz.mapper.SysRoleMenuMapper;
import com.cloud.oauth.gomoney.biz.mapper.SysUserMapper;
import com.cloud.oauth.gomoney.biz.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    private final SysUserMapper userMapper;

    @Transactional(readOnly = true)
    public PageUtils findSysRoleByPage(Map<String, Object> params) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (params.get("roleName") != null && StringUtils.isNotBlank(params.get("roleName").toString())) {
            queryWrapper.like("role_name", params.get("roleName"));
        }
        int current = Integer.parseInt(params.get("page").toString());
        int size = Integer.parseInt(params.get("limit").toString());
        Page<SysRole> pages = new Page<>(current, size);
        IPage<SysRole> page = roleMapper.selectPage(pages, queryWrapper);
        return new PageUtils(page);
    }

    @Transactional(readOnly = true)
    public List<SysRole> findSysRoleAll() {
        return roleMapper.selectList(null);
    }

    @Transactional(readOnly = true)
    public List<SysRole> findSysRoleByUserId(String id) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        return roleMapper.selectList(queryWrapper);
    }

    @Transactional(readOnly = true)
    public SysRole findSysRoleByRoleId(String id) {
        SysRole role = roleMapper.selectById(id);
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<SysRoleMenu>();
        queryWrapper.eq("role_id", id);
        //查询角色对应的菜单
        List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);
        List<String> menusList = new ArrayList<String>();
        for (SysRoleMenu roleMenu : roleMenus) {
            menusList.add(roleMenu.getMenuId());
        }
        menusList.add("-666666");
        role.setMenuIdList(menusList);
        return role;
    }

    public void addSysRole(SysRole sysRole) {
        roleMapper.insert(sysRole);
        if (sysRole.getMenuIdList() != null && sysRole.getMenuIdList().size() > 0) {
            for (String menu : sysRole.getMenuIdList()) {
                if (!menu.equals("-666666")) {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(sysRole.getRoleId());
                    roleMenu.setMenuId(menu);
                    roleMenuMapper.insert(roleMenu);
                }
            }
        }
    }

    public void delSysRoleById(String[] ids) {
        for (String id : ids) {
            userMapper.deleteById(id);
            QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", id);
            roleMenuMapper.delete(queryWrapper);
            roleMapper.deleteById(id);
        }
    }

    public void updateSysRole(SysRole sysRole) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<SysRoleMenu>();
        queryWrapper.eq("role_id", sysRole.getRoleId());
        roleMenuMapper.delete(queryWrapper);
        roleMapper.updateById(sysRole);
        for (String menu : sysRole.getMenuIdList()) {
            if (!menu.equals("-666666")) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(sysRole.getRoleId());
                roleMenu.setMenuId(menu);
                roleMenuMapper.insert(roleMenu);
            }
        }
    }
}
