package com.cloud.oauth.gomoney.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.oauth.gomoney.api.entity.sys.SysDept;
import com.cloud.oauth.gomoney.biz.mapper.SysDeptMapper;
import com.cloud.oauth.gomoney.biz.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper deptMapper;

    public List<SysDept> findDeptForAll() {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysDept::getCreateTime);
        List<SysDept> depts = deptMapper.selectList(queryWrapper);
        depts.forEach(d -> {
            d.setParentDept(deptMapper.selectById(d.getParentId()));
        });
        depts = findDepts(depts);
        return depts;
    }

    public List<SysDept> findDepts(List<SysDept> ms) {
        List<SysDept> depts = new ArrayList<>();
        ms.forEach(m -> {
            if (m.getParentId() == null) {
                depts.add(m);
            }
        });
        depts.forEach(dept -> {
            dept.setChildren(findChild(dept, ms));
        });
        return depts;
    }

    public List<SysDept> findChild(SysDept dept, List<SysDept> depts) {
        List<SysDept> childs = new ArrayList<>();
        for (SysDept m : depts) {
            if (dept.getDeptId().equals(m.getParentId())) {
                childs.add(m);
            }
        }
        childs.forEach(m -> {
            m.setChildren(findChild(m, depts));
        });
        if (childs.size() == 0) {
            return null;
        }
        return childs;
    }


    @Override
    public void insertDept(SysDept dept) {
        SysDept parent = deptMapper.selectById(dept.getParentId());
        if (parent != null) {
            dept.setLevel(parent.getLevel() + 1);
        } else {
            dept.setLevel(1);
        }
        dept.setState(1);
        dept.setCreateTime(new Timestamp(new java.util.Date().getTime()));
        deptMapper.insert(dept);
    }

    @Override
    public SysDept findById(String id) {
        SysDept dept = deptMapper.selectById(id);
        return dept;
    }

    @Override
    public void updateDept(SysDept dept) {
        SysDept oldDept = deptMapper.selectById(dept.getDeptId());
        if (oldDept != null) {
            dept.setLevel(oldDept.getLevel() + 1);
        } else {
            dept.setLevel(1);
        }
        dept.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        dept.setCreateByUserId(oldDept.getCreateByUserId());
        dept.setCreateTime(oldDept.getCreateTime());
        deptMapper.updateById(dept);
    }
}
