package com.cloud.oauth.gomoney.api.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("sys_user_dept")
public class SysUserDept implements Serializable {

    @Serial
    private static final long serialVersionUID = 8807137955457871617L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String userDeptId;

    private String userId;

    private String deptId;
}
