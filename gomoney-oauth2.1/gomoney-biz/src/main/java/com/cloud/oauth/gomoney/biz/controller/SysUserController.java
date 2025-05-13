package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.annotation.Resubmit;
import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;
import com.cloud.oauth.gomoney.api.entity.sys.SysOAuthClientDetails;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.form.RefreshForm;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.common.utils.RedisUtil;
import com.cloud.oauth.gomoney.biz.service.impl.SysMenuServiceImpl;
import com.cloud.oauth.gomoney.biz.service.impl.SysOAuthClientDetailsServiceImpl;
import com.cloud.oauth.gomoney.biz.service.impl.SysUserServiceImpl;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/sys/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SysUserController extends AbstractController {

    private final SysUserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    private final SysOAuthClientDetailsServiceImpl sysOAuthClientDetailsService;

    private final RedisUtil redisUtil;

    @PostMapping("/save/refreshToken")
    public R saveRefreshToken(@RequestBody RefreshForm refreshForm) {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String refresh_token = refreshForm.getRefreshToken();
        SysOAuthClientDetails clientDetails = sysOAuthClientDetailsService.findById(refreshForm.getClientId());
        int refresh_token_validity = clientDetails.getRefreshTokenValidity();
        redisUtil.set(token, refresh_token, refresh_token_validity);
        return R.ok();
    }

    @Operation(summary = "用户信息查询")
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:user:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils pages = userService.list(params);
        return R.ok().put("data", pages);
    }
    @GetMapping("/lists")
    @Operation(parameters = {
            @Parameter(name = "name"),
            @Parameter(name = "page", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "limit", required = true, in = ParameterIn.QUERY)
    })
    public R lists(@RequestParam Map<String, Object> params) {
        PageUtils pages = userService.list(params);
        return R.ok().put("data", pages);
    }

    @Resubmit
    @PostMapping("/password")
    public R changePwd(@RequestBody Map<String, Object> params) {
        SysUser user = getUser();
        boolean flag = passwordEncoder.matches(params.get("password").toString(), user.getUserPass());
        if (!flag) {
            return R.error("原密码不正确");
        }
        SysUser sysUser = userService.findSysUserById(user.getUserId());
        sysUser.setUserPass(passwordEncoder.encode(sysUser.getUserPass()));
        userService.updatePassword(sysUser);
        return R.ok();
    }


    @GetMapping("/sysUser")
    public R sysUser() {
        SysUser user = getUser();
        return R.ok().put("user", user);
    }

    @GetMapping("/findById/{userId}")
    @PreAuthorize("@pms.hasPermission('sys:user:info')")
    public R findById(@PathVariable String userId) {
        SysUser user = userService.findSysUserById(userId);
        return R.ok().put("data", user);
    }

    @GetMapping("/self")
    public R self() {
        SysUser user = userService.findSysUserById(getUser().getUserId());
        return R.ok().put("user", user);
    }

    @Resubmit
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('sys:user:save')")
    public R save(@RequestBody SysUser user) {
        user.setOptrdate(new Timestamp(new Date().getTime()));
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        userService.saveSysUser(user);
        return R.ok();
    }

    @Resubmit
    @PostMapping("/update")
    @PreAuthorize("@pms.hasPermission('sys:user:update')")
    public R update(@RequestBody SysUser user) {
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        userService.updateSysUser(user);
        return R.ok();
    }


    @PostMapping("/checkname")
    public R checkName(@RequestBody Map<String, Object> params) {
        SysUser user = userService.findSysUserByName(params.get("name").toString());
        return R.ok().put("data", user);
    }

    private final SysMenuServiceImpl menuService;

    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    @Inner
    @GetMapping("/info/{username}")
    public SysUser info(@PathVariable String username) {
        SysUser user = userService.findSysUserByName(username);
        List<SysMenu> menus = new ArrayList<>();
        Set<String> permsSet = new HashSet<>();
        if (user.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
            menus = menuService.findMenuForAll(1);
            permsSet = menuService.getAllPermissions(menus);
        } else {
            permsSet = menuService.getUserPermissions(user.getUserId());
        }
        user.setPermsSet(permsSet);
        return user;
    }

    @Inner
    @GetMapping("/findByMobile/{mobile}")
    public SysUser findByMobile(@PathVariable String mobile){
        return userService.findSysUserByMobile(mobile);
    }

    @Inner
    @GetMapping("/infoByUserId/{userId}")
    public SysUser infoByUserId(@PathVariable String userId) {
        return userService.findSysUserById(userId);
    }

    @Inner
    @GetMapping("/authorized/{username}/{password}")
    public SysUser authorizedByPassword(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userService.authorizedByPassword(username, password);
    }

    @Resubmit
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('sys:user:delete')")
    public R delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, "06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
            return R.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUser().getUserId())) {
            return R.error("当前用户不能删除");
        }
        userService.delSysUser(userIds);
        return R.ok();
    }
}
