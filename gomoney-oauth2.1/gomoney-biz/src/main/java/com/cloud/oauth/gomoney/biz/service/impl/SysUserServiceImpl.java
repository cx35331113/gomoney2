package com.cloud.oauth.gomoney.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.oauth.gomoney.api.entity.sys.*;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.common.utils.RedisUtil;
import com.cloud.oauth.gomoney.biz.common.utils.TokenGenerator;
import com.cloud.oauth.gomoney.biz.mapper.*;
import com.cloud.oauth.gomoney.biz.service.SysUserService;
import com.cloud.oauth.gomoney.core.utils.R;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    private final SysUserMapper userMapper;

    private final SysUserRoleMapper userRoleMapper;

    private final SysMenuServiceImpl menuService;

    private final RedisUtil redisUtil;

    public R createToken(String id) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        if (redisUtil.getAll("*") != null) {
            Set<String> keys = redisUtil.getAll("*");
            for (Object k : keys) {
                try {
                    if (redisUtil.get(k.toString()).toString().equals(id)) {
                        redisUtil.del(k.toString());
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        SysUser user = userMapper.selectById(id);
        Set<String> permsSet = new HashSet<>();
        List<SysMenu> menus = new ArrayList<>();
        //用户权限列表
        if (id.equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
            menus = menuService.findMenuForAll(1);
            permsSet = menuService.getAllPermissions(menus);
        } else {
            permsSet = menuService.getUserPermissions(id);
        }
        user.setPermsSet(permsSet);
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        redisUtil.set(token, id, EXPIRE);
        redisUtil.set(id, user, EXPIRE);
        R r = R.ok().put("token", token).put("expire", EXPIRE);
        return r;
    }

    @Transactional(readOnly = true)
    public SysUser login(String name) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", name);
        SysUser user = userMapper.selectOne(queryWrapper);
        return user;
    }

/*    @Autowired
    private BizFeignService bizFeignService;*/

    @Transactional(readOnly = true)
    public PageUtils list(Map<String, Object> params) {
/*        params.put("code",1);
        R r = bizFeignService.insert(params);*/
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (params.get("userName") != null && !StringUtils.isBlank(params.get("userName").toString())) {
            queryWrapper.like("user_name", params.get("userName"));
        }
        int current = Integer.parseInt(params.get("page").toString());
        int size = Integer.parseInt(params.get("limit").toString());
        Page<SysUser> pages = new Page<>(current, size);
        IPage<SysUser> page = userMapper.selectPage(pages, queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public void updatePassword(SysUser user) {
        userMapper.updatePassword(user);
    }

    private final SysDeptMapper deptMapper;

    @Transactional(readOnly = true)
    public SysUser findSysUserById(String id) {
        SysUser user = userMapper.selectById(id);
        QueryWrapper<SysUserRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("user_id", id);
        userRoleMapper.selectList(roleQueryWrapper).forEach(r -> {
            user.getRoleIdList().add(r.getRoleId());
        });
        SysDept dept = deptMapper.selectById(user.getDeptId());
        if (dept != null) {
            user.setName(dept.getName());
        }
        return user;
    }

    private final PasswordEncoder passwordEncoder;

    public SysUser authorizedByPassword(String username, String password) {
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getState, 1)
                .eq(SysUser::getUserName, username)
                .or()
                .eq(SysUser::getMobile, username)
        );
        //封装权限
        if (!ObjectUtils.isEmpty(sysUser) && passwordEncoder.matches(password, sysUser.getUserPass())) {
            Set<String> permsSet = new HashSet<>();
            List<SysMenu> menus = new ArrayList<>();
            //用户权限列表
            if (sysUser.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
                menus = menuService.findMenuForAll(1);
                permsSet = menuService.getAllPermissions(menus);
            } else {
                permsSet = menuService.getUserPermissions(sysUser.getUserId());
            }
            sysUser.setPermsSet(permsSet);
        }
        return sysUser;
    }

    @Transactional(readOnly = true)
    public SysUser findSysUserByName(String name) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, name)
                .or().eq(SysUser::getMobile, name));
        Set<String> permsSet = new HashSet<>();
        List<SysMenu> menus = new ArrayList<>();
        //用户权限列表
        if (user != null) {
            if (user.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
                menus = menuService.findMenuForAll(1);
                permsSet = menuService.getAllPermissions(menus);
            } else {
                permsSet = menuService.getUserPermissions(user.getUserId());
            }
            user.setPermsSet(permsSet);
        }
        return user;
    }

    public SysUser findSysUserByMobile(String mobile) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, mobile));
        Set<String> permsSet = new HashSet<>();
        List<SysMenu> menus = new ArrayList<>();
        //用户权限列表
        if (user != null) {
            if (user.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
                menus = menuService.findMenuForAll(1);
                permsSet = menuService.getAllPermissions(menus);
            } else {
                permsSet = menuService.getUserPermissions(user.getUserId());
            }
            user.setPermsSet(permsSet);
        }
        return user;
    }

    private final SysUserDeptMapper sysUserDeptMapper;

    public void saveSysUser(SysUser user) {
        userMapper.insert(user);
        if (user.getRoleIdList() != null && user.getRoleIdList().size() >= 0) {
            user.getRoleIdList().forEach(id -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(id);
                userRoleMapper.insert(userRole);
            });
        }
        if (user.getUserDeptList() != null && user.getUserDeptList().size() >= 0) {
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setUserId(user.getUserId());
            sysUserDept.setUserId(user.getDeptId());
            sysUserDeptMapper.insert(sysUserDept);
        }
    }

    @Override
    public void updateSysUser(SysUser user) {
        userMapper.updateById(user);
        if (user.getRoleIdList() != null && user.getRoleIdList().size() > 0) {
            userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getUserId()));
            user.getRoleIdList().forEach(id -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(id);
                userRoleMapper.insert(userRole);
            });
        }
        if (user.getUserDeptList() != null && user.getUserDeptList().size() >= 0) {
            sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().eq(SysUserDept::getDeptId, user.getUserId()));
            SysUserDept sysUserDept = new SysUserDept();
            sysUserDept.setUserId(user.getUserId());
            sysUserDept.setUserId(user.getDeptId());
            sysUserDeptMapper.insert(sysUserDept);
        }
        if (user.getState() != 1) {
            if (redisUtil.getAll("*") != null) {
                Set<String> keys = redisUtil.getAll("*");
                for (Object k : keys) {
                    try {
                        OAuth2Authorization auth2AuthorizationConsent = (OAuth2Authorization) redisUtil.get(k.toString());
                        if (auth2AuthorizationConsent.getPrincipalName().equals(user.getUserName())) {
                            redisUtil.del(k.toString());
                        }
                    } catch (Exception e) {


                    }
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public SysUser findSysUserByToken(String token) {
        return userMapper.findSysUserByToken(token);
    }

    @Override
    public void delSysUser(String[] ids) {
        for (String id : ids) {
            SysUser user = userMapper.selectById(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("user_id", id);
            userRoleMapper.deleteByMap(map);
            userMapper.deleteById(id);
            if (redisUtil.getAll("*") != null) {
                Set<String> keys = redisUtil.getAll("*");
                for (Object k : keys) {
                    try {
                        OAuth2Authorization auth2AuthorizationConsent = (OAuth2Authorization) redisUtil.get(k.toString());
                        if (auth2AuthorizationConsent.getPrincipalName().equals(user.getUserName())) {
                            redisUtil.del(k.toString());
                        }
                    } catch (Exception e) {


                    }
                }
            }
        }
    }
}
