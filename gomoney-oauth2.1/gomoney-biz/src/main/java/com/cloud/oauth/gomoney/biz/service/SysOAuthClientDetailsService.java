package com.cloud.oauth.gomoney.biz.service;

import com.cloud.oauth.gomoney.api.entity.sys.SysOAuthClientDetails;
import com.cloud.oauth.gomoney.api.utils.PageUtils;

import java.util.Map;

public interface SysOAuthClientDetailsService {

    SysOAuthClientDetails findById(String id);

    PageUtils list(Map<String, Object> params);
}
