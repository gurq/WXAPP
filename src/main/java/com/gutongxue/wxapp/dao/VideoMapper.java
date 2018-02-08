package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.domain.VideoDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {
    @Insert("insert into `gtx_base_video` (`openid`,`gmt_create`,`gmt_modified`,`video_url`,`video_cover`,`video_description`,`video_source`,`video_status`)\n" +
            " values(#{video.openid},#{video.createTime},#{video.modifiedTime},#{video.url},#{video.cover},#{video.description},#{video.source},#{video.status})")
    void insertVideo(@Param("video")VideoDO videoDO);

    @Results({
            @Result(property = "id" , column = "id"),
            @Result(property = "openid" , column = "openid"),
            @Result(property = "createTime" , column = "gmt_create"),
            @Result(property = "modifiedTime" , column = "gmt_modified"),
            @Result(property = "url" , column = "video_url"),
            @Result(property = "cover" , column = "video_cover"),
            @Result(property = "description" , column = "video_description"),
            @Result(property = "source" , column = "video_source"),
            @Result(property = "status" , column = "video_status")

    })
    @Select("select * from gtx_base_video order by gmt_modified desc")
    List<VideoDO> listVideo();
}
