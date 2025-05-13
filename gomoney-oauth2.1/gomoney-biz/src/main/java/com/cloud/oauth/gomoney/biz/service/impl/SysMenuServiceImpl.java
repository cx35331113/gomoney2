package com.cloud.oauth.gomoney.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.biz.mapper.SysMenuMapper;
import com.cloud.oauth.gomoney.biz.service.SysMenuService;
import com.cloud.oauth.gomoney.core.utils.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper menuMapper;


    public List<SysMenu> findMenus() {
        List<SysMenu> menuList = menuMapper.selectList(new QueryWrapper<>());
        menuList = findChildrenMenu(menuList);
        return menuList;
    }

    public List<SysMenu> nav(SysUser user) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        if (user.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
            menus = findMenuForAll(1);
        } else {
            menus = findMenusByUserId(user.getUserId());
        }
        menus = findPermissions(menus);
        return menus;
    }
    public Set<String> permissions(SysUser user){
        return user.getPermsSet();
    }


    public Set<String> getAllPermissions(List<SysMenu> menus) {
        List<String> permsList = new ArrayList<String>();
        menus.forEach(menu -> {
            permsList.add(menu.getPerms());
        });
        Set<String> permsSet = new HashSet<String>();
        permsList.forEach(perms -> {
            if (!StringUtils.isBlank(perms)) {
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        });
        return permsSet;
    }

    public Set<String> getUserPermissions(String id) {
        List<String> permsList = new ArrayList<String>();
        menuMapper.findMenusByUserId(id).forEach(menu -> {
            permsList.add(menu.getPerms());
        });
        Set<String> permsSet = new HashSet<String>();
        permsList.forEach(perms -> {
            if (!StringUtils.isBlank(perms)) {
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        });
        return permsSet;
    }

    @Transactional(readOnly = true)
    public List<SysMenu> findMenusByUserId(String id) {
        return menuMapper.findMenusByUserId(id);
    }

    @Transactional(readOnly = true)
    public SysMenu findMenuById(String id) {
        SysMenu menu = menuMapper.selectById(id);
        if (menu != null) {
            SysMenu parentMenuEntity = findMenuById(menu.getParentMenuId());
            if (parentMenuEntity != null) {
                menu.setParentName(parentMenuEntity.getMenuName());
            }
        }
        return menu;
    }

    public void addMenu(SysMenu m) {
        if (StringUtils.isNoneBlank(m.getParentMenuId())) {
            SysMenu parent = menuMapper.selectById(m.getParentMenuId());
            m.setMenuLevel(parent.getMenuLevel() + 1);
        }
        menuMapper.insert(m);
    }


    @Transactional(readOnly = true)
    public List<SysMenu> findMenuForAll(Integer id) {
        List<SysMenu> menuList = menuMapper.selectList(null);
        for (SysMenu sysMenuEntity : menuList) {
            SysMenu parentMenuEntity = findMenuById(sysMenuEntity.getParentMenuId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getMenuName());
            }
        }
        return menuList;
    }



    @Transactional(readOnly = true)
    public List<SysMenu> findChildMenuById(String id) {
        return menuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentMenuId, id));
    }

    @Transactional(readOnly = true)
    public List<SysMenu> findPermissions(List<SysMenu> ms) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        ms.forEach(m -> {
            if (m.getParentMenuId() == null) {
                menus.add(m);
            }
        });
        menus.forEach(menu -> {
            menu.setChildren(findChild(menu, ms));
        });
        return menus;
    }

    public List<SysMenu> selectMenus(){
        List<SysMenu> menuList = menuMapper.selectList(null);
        for (SysMenu sysMenuEntity : menuList) {
            SysMenu parentMenuEntity = findMenuById(sysMenuEntity.getParentMenuId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getMenuName());
            }
        }
        List<SysMenu> menus = new ArrayList<SysMenu>();
        menuList.forEach(m -> {
            if (m.getParentMenuId() == null) {
                menus.add(m);
            }
        });
        menus.forEach(menu -> {
            menu.setChildren(findChild(menu, menuList));
        });
        return menus;
    }

    public List<SysMenu> findChildrenMenu(List<SysMenu> ms) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        ms.forEach(m -> {
            if (m.getParentMenuId() == null) {
                menus.add(m);
            }
        });
        menus.forEach(menu -> {
            menu.setChildren(findChildren(menu, ms));
        });
        return menus;
    }

    public List<SysMenu> findChildren(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> childs = new ArrayList<SysMenu>();
        menus.forEach(m -> {
            if (menu.getMenuId().equals(m.getParentMenuId())) {
                childs.add(m);
            }
        });
        childs.forEach(m -> {
            m.setChildren(findChildren(m, menus));
        });
        if (childs.size() == 0) {
            return null;
        }
        return childs;
    }

    public List<SysMenu> findChild(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> childs = new ArrayList<SysMenu>();
        menus.forEach(m -> {
            if (menu.getMenuId().equals(m.getParentMenuId()) && m.getType() != 2) {
                childs.add(m);
            }
        });
        childs.forEach(m -> {
            m.setChildren(findChild(m, menus));
        });
        if (childs.size() == 0) {
            return null;
        }
        return childs;
    }

    public void updateMenu(SysMenu m) {
        menuMapper.updateById(m);
    }

    public void delMenu(String id) {
        menuMapper.deleteById(id);
    }

}
