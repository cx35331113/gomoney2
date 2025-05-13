package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.annotation.Resubmit;
import com.cloud.oauth.gomoney.api.entity.sys.SysDept;
import com.cloud.oauth.gomoney.biz.service.impl.SysDeptServiceImpl;
import com.cloud.oauth.gomoney.core.utils.R;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/dept")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@Tag(name = "部门管理")
@RequiredArgsConstructor
public class SysDeptController extends AbstractController {

    private final SysDeptServiceImpl deptService;


    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:dept:list')")
    public R list() {
        return R.ok().put("data",deptService.findDeptForAll());
    }

    @GetMapping("/select")
    public R select() {
        return R.ok().put("data",deptService.findDeptForAll());
    }

    @Resubmit
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('sys:dept:save')")
    public R save(@RequestBody SysDept dept) {
        dept.setCreateByUserId(getUser().getUserId());
        deptService.insertDept(dept);
        return R.ok();
    }
}
