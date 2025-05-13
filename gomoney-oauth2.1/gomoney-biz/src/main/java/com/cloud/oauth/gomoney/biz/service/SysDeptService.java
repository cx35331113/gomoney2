package com.cloud.oauth.gomoney.biz.service;

import com.cloud.oauth.gomoney.api.entity.sys.SysDept;
import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;

import java.util.List;

public interface SysDeptService {

    List<SysDept> findDeptForAll();

    void insertDept(SysDept dept);

    SysDept findById(String id);

    void updateDept(SysDept dept);
}
