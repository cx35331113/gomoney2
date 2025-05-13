package com.cloud.oauth.gomoney.workflow.service;

import com.cloud.oauth.gomoney.api.utils.PageUtils;
import com.cloud.oauth.gomoney.workflow.entity.MaterInfo;

import java.util.Map;

public interface MaterInfoService {

    PageUtils list(Map<String, Object> params);

    void insert(MaterInfo mater);

}
