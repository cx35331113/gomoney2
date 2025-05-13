package com.cloud.oauth.gomoney.biz.controller;

import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.service.impl.SysOAuthClientDetailsServiceImpl;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/client")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class SysOAuthClientDetailsController {

    private final SysOAuthClientDetailsServiceImpl sysOAuthClientDetailsService;

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:client:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOAuthClientDetailsService.list(params);
        return R.ok().put("data", page);
    }

    @Inner
    @GetMapping("/getClientDetailsById/{clientId}")
    public R getClientDetailsById(@PathVariable String clientId){
        return R.ok().put("client",sysOAuthClientDetailsService.findById(clientId));
    }
}
