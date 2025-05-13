package com.cloud.oauth.gomoney.api.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1846451378258499364L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private String roleId;
}
