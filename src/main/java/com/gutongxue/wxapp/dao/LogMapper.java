package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.LogDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("insert into gtx_log_crawler (log_time,log_content) values (#{time},#{content})")
    boolean insertCrawlerLog(LogDO logDO);

}
