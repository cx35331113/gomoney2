package com.cloud.oauth.gomoney.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.oauth.gomoney.api.entity.sys.SysOAuthClientDetails;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.biz.mapper.SysOAuthClientDetailsMapper;
import com.cloud.oauth.gomoney.biz.service.SysOAuthClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SysOAuthClientDetailsServiceImpl implements SysOAuthClientDetailsService {

    private final SysOAuthClientDetailsMapper detailsMapper;

    public SysOAuthClientDetails findById(String id) {
        return detailsMapper.selectById(id);
    }

    @Transactional(readOnly = true)
    public PageUtils list(Map<String, Object> params) {
        LambdaQueryWrapper<SysOAuthClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        if (params.get("name") != null && !StringUtils.isBlank(params.get("name").toString())) {
            queryWrapper.like(SysOAuthClientDetails::getClientId, params.get("name"));
        }
        int current = Integer.parseInt(params.get("page").toString());
        int size = Integer.parseInt(params.get("limit").toString());
        Page<SysOAuthClientDetails> pages = new Page<>(current, size);
        IPage<SysOAuthClientDetails> page = detailsMapper.selectPage(pages, queryWrapper);
        return new PageUtils(page);
    }
}
