package com.cloud.oauth.gomoney.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.oauth.gomoney.api.entity.sys.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select distinct m.* " +
            "  from sys_menu m " +
            "  inner join sys_role_menu r on m.menu_id=r.menu_id " +
            "  inner join sys_role sr on sr.role_id=r.role_id " +
            "  inner join sys_user_role ur on ur.role_id=sr.role_id " +
            "  inner join sys_user u on u.user_id=ur.user_id " +
            "  where u.user_id = #{id}")
    List<SysMenu> findMenusByUserId(String id);
}
