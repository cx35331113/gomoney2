package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.annotation.Resubmit;
import com.cloud.oauth.gomoney.api.entity.sys.SysRole;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.common.utils.ValidatorUtils;
import com.cloud.oauth.gomoney.biz.service.impl.SysRoleServiceImpl;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/role")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SysRoleController extends AbstractController {

    private final SysRoleServiceImpl roleService;

    @Inner
    @PostMapping("/add")
    public void add(@RequestParam Map<String,Object> params){
        SysRole role=new SysRole();
        role.setRoleName(params.get("name").toString());
        roleService.addSysRole(role);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @PreAuthorize("@pms.hasPermission('sys:role:select')")
    public R select() {
        List<SysRole> list = roleService.findSysRoleAll();
        return R.ok().put("data", list);
    }

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = roleService.findSysRoleByPage(params);
        return R.ok().put("data", page);
    }

    @Resubmit
    @PostMapping("/saveOrUpdate")
    @PreAuthorize("@pms.hasPermission('sys:role:save')")
    public R save(@RequestBody SysRole role) {
        SysUser user = getUser();
        role.setUserId(user.getUserId());
        role.setCreateTime(new Timestamp(new Date().getTime()));
        roleService.addSysRole(role);
        return R.ok();
    }

    /**
     * 角色信息
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("@pms.hasPermission('sys:role:info')")
    public R info(@PathVariable String roleId) {
        SysRole role = roleService.findSysRoleByRoleId(roleId);
        return R.ok().put("data", role);
    }

    /**
     * 修改角色
     */
    @Resubmit
    @PutMapping("/saveOrUpdate")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public R update(@RequestBody SysRole role) {
        //ValidatorUtils.validateEntity(role);
        SysUser user = getUser();
        role.setUserId(user.getUserId());
        role.setCreateTime(new Timestamp(new Date().getTime()));
        roleService.updateSysRole(role);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @Resubmit
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    public R delete(@RequestBody String[] roleIds) {
        roleService.delSysRoleById(roleIds);
        return R.ok();
    }
}
