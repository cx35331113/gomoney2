package com.cloud.oauth.gomoney.api.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName("sys_dept")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1384844036687809952L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String deptId;

    private String name;

    private int sortOrder;

    private int state;

    private String parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    private String createByUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;

    private String userId; //操作人

    private int level;

    @TableField(exist = false)
    private SysDept parentDept;

    @TableField(exist = false)
    private List<SysDept> children;


}
