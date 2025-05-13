package com.cloud.oauth.gomoney.api.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 6442744618563231428L;

    @TableId(type = IdType.ASSIGN_ID)
    private String menuId;

    private String menuName;

    private String url;

    private String perms;

    private int type;

    private String icon;

    private int menuLevel;

    private String parentMenuId;

    private int orderNum;

    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<SysMenu> children=new ArrayList<SysMenu>();
}

