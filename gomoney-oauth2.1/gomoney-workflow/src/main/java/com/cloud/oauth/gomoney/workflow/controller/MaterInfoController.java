package com.cloud.oauth.gomoney.workflow.controller;

import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.workflow.entity.MaterInfo;
import com.cloud.oauth.gomoney.workflow.service.impl.MaterInfoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mater/info")
@AllArgsConstructor
public class MaterInfoController extends AbstractController {

    private final MaterInfoServiceImpl materInfoService;

    @GetMapping("/list")
    //@PreAuthorize("@pms.hasPermission('mater:info:list')")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = materInfoService.list(params);
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    @PreAuthorize("@pms.hasPermission('mater:info:insert')")
    public R insert(@RequestBody MaterInfo mater) {
        mater.setUserId(getUser().getUserId());
        materInfoService.insert(mater);
        return R.ok();
    }
}
