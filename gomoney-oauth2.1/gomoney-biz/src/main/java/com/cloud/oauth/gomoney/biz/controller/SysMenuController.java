package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.annotation.Resubmit;
import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;
import com.cloud.oauth.gomoney.biz.common.utils.RRException;
import com.cloud.oauth.gomoney.biz.service.impl.SysMenuServiceImpl;
import com.cloud.oauth.gomoney.core.utils.ConstantProperty;
import com.cloud.oauth.gomoney.core.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/menu")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@Tag(name = "菜单管理模块")
@RequiredArgsConstructor
public class SysMenuController extends AbstractController {

    private final SysMenuServiceImpl sysMenuService;

    @Operation(summary = "登陆后获取登录用户菜单信息")
    @GetMapping("/nav")
    public R nav() {
        return R.ok().put("data",sysMenuService.nav(getUser()));
    }

    @GetMapping("/permissions")
    public R permissions() {
        return R.ok().put("data",sysMenuService.permissions(getUser()));
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:menu:list')")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("data", sysMenuService.findMenus());
    }

    @GetMapping("/select")
    @PreAuthorize("@pms.hasPermission('sys:menu:select')")
    public R select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.selectMenus();
        return R.ok().put("data", menuList);
    }

    @Resubmit
    @PostMapping("/saveOrUpdate")
    @PreAuthorize("@pms.hasPermission('sys:menu:save')")
    public R save(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.addMenu(menu);
        return R.ok();
    }

    @PutMapping("/saveOrUpdate")
    @PreAuthorize("@pms.hasPermission('sys:menu:update')")
    public R update(@RequestBody SysMenu menu) {
        //数据校验 
        verifyForm(menu);
        sysMenuService.updateMenu(menu);
        return R.ok();
    }

    /**
     * 删除
     */
    @Resubmit
    @DeleteMapping("/delete/{menuId}")
    @PreAuthorize("@pms.hasPermission('sys:menu:delete')")
    public R delete(@PathVariable String menuId) {
/*        if(params.get("menuId").equals("MOO1")){
            return R.error("系统菜单，不能删除");
        }*/
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.findChildMenuById(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }
        sysMenuService.delMenu(menuId);
        return R.ok();
    }

    @Operation(summary = "查看菜单信息")
    @GetMapping("/{menuId}")
    @PreAuthorize("@pms.hasPermission('sys:menu:info')")
    public R info(@PathVariable String menuId) {
        SysMenu menu = sysMenuService.findMenuById(menuId);
        return R.ok().put("data", menu);
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getMenuName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (menu.getParentMenuId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        if (menu.getParentMenuId().equals(menu.getMenuId())) {
            throw new RRException("上级菜单不能事当前菜单");
        }

        //菜单
        if (menu.getType() == ConstantProperty.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = ConstantProperty.MenuType.CATALOG.getValue();
        if (!menu.getParentMenuId().equals("1")) {
            SysMenu parentMenu = sysMenuService.findMenuById(menu.getParentMenuId());
            parentType = parentMenu.getType();
        } else {
            menu.setParentMenuId(null);
        }

        //目录、菜单
        if (menu.getType() == ConstantProperty.MenuType.CATALOG.getValue() ||
                menu.getType() == ConstantProperty.MenuType.MENU.getValue()) {
            if (parentType != ConstantProperty.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == ConstantProperty.MenuType.BUTTON.getValue()) {
            if (parentType != ConstantProperty.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
