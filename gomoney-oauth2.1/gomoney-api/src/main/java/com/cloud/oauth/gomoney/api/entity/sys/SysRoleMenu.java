package com.cloud.oauth.gomoney.api.entity.sys;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 5596553626857721347L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String roleId;

    @TableField(exist = false)
    private SysRole role;

    private String menuId;

    @TableField(exist = false)
    private SysMenu menu;

}
