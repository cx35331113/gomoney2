package com.cloud.oauth.gomoney.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.workflow.entity.MaterInfo;
import com.cloud.oauth.gomoney.workflow.mapper.MaterInfoMapper;
import com.cloud.oauth.gomoney.workflow.service.MaterInfoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
public class MaterInfoServiceImpl implements MaterInfoService {

    private final MaterInfoMapper materInfoMapper;

    public PageUtils list(Map<String, Object> params) {
        QueryWrapper<MaterInfo> queryWrapper = new QueryWrapper<>();
        if (params.get("name") != null && !StringUtils.isBlank(params.get("name").toString())) {
            queryWrapper.like("matter_name", params.get("name"));
        }
        queryWrapper.eq("state",1);
        queryWrapper.orderByDesc("optrdate");
        int current = Integer.parseInt(params.get("page").toString());
        int size = Integer.parseInt(params.get("limit").toString());
        Page<MaterInfo> pages = new Page<>(current, size);
        IPage<MaterInfo> page = materInfoMapper.selectPage(pages, queryWrapper);
        return new PageUtils(page);
    }

    public void insert(MaterInfo mater) {
        mater.setState(1);
        mater.setOptrdate(new Timestamp(new java.util.Date().getTime()));
        materInfoMapper.insert(mater);
    }
}
