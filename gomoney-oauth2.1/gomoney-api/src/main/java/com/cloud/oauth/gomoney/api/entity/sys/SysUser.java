package com.cloud.oauth.gomoney.api.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 7721677459009101644L;

    @TableId(type = IdType.ASSIGN_ID)
    private String userId;

    private String userName;

    private String userPass;

    private String realname;

    private String userEmail;

    private String mobile;

    private int state;

    private int enabled;

    private String deptId;

    @TableField(exist = false)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp optrdate;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private Set<String> userDeptList;

    @TableField(exist = false)
    private List<String> roleIdList=new ArrayList<String>();

    @TableField(exist = false)
    private Set<String> permsSet = new HashSet<String>();
}
