package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
@Mapper
public interface UserMapper {

    @Insert("insert into `gtx_user_info` (`openid`,`gmt_create`,`gmt_login`,`nickname`,`avatar`,`ip`,`country`,`region`,`city`,`isp`)\n" +
            " values(#{user.openid},#{user.createTime},#{user.loginTime},#{user.nickname},#{user.avatar},#{user.ip},#{user.country},#{user.region},#{user.city},#{user.isp})")
    void insertUser(@Param("user")UserDO userDO);

    @Delete("delete from gtx_user_info where openid = #{0}")
    void deleteUser(String openid);

    @Update("update `gtx_user_info` SET \n" +
            "    `gmt_login`=#{user.loginTime},\n" +
            "    `nickname`=#{user.nickname},\n" +
            "    `avatar`=#{user.avatar},\n" +
            "    `ip`=#{user.ip},\n" +
            "    `country`=#{user.country},\n" +
            "    `region`=#{user.region},\n" +
            "    `city`=#{user.city},\n" +
            "    `isp`=#{user.isp}\n" +
            "  where openid = #{user.openid}")
    void updateUser(@Param("user") UserDO userDO);

    @Results(id = "userDO" , value = {
            @Result(property = "openid" , column = "openid"),
            @Result(property = "createTime" , column = "gmt_creata"),
            @Result(property = "loginTime" , column = "gmt_login"),
            @Result(property = "nickname" , column = "nickname"),
            @Result(property = "avatar" , column = "avatar"),
            @Result(property = "ip" , column = "ip"),
            @Result(property = "country" , column = "country"),
            @Result(property = "region" , column = "region"),
            @Result(property = "city" , column = "city"),
            @Result(property = "isp" , column = "isp")
    })
    @Select("select * from gtx_user_info order by gmt_login desc")
    List<UserDO> listUser();

    @Select("select count(*) from gtx_user_info")
    int countUser();

    @ResultMap("userDO")
    @Select("select * from gtx_user_info where openid = #{0}")
    UserDO getUser(String openid);

    @Select("select count(*) from gtx_user_info where openid = #{0}")
    int countUserByOpenid(String openid);

}
