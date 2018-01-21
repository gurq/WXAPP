package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.JokeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MybatisDao {
    @Results({
            @Result(property = "id" , column = "id"),
            @Result(property = "content" , column = "joke_content"),
            @Result(property = "source" , column = "joke_source"),
            @Result(property = "createTime" , column = "gmt_create"),
            @Result(property = "modifiedTime" , column = "gmt_modified")
    })
    @Select("select * from gtx_base_joke order by id desc")
    List<JokeDO> listJoke();

}
