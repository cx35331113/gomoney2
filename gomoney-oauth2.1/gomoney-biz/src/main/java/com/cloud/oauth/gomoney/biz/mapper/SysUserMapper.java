package com.cloud.oauth.gomoney.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.oauth.gomoney.api.entity.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select u.* from sys_user u inner join sys_user_token t on u.user_id=t.user_id where t.token = #{token}")
    SysUser findSysUserByToken(String token);

    @Update("update sys_user set user_pass=#{userPass} where user_id=#{userId}")
    void updatePassword(SysUser user);
}
