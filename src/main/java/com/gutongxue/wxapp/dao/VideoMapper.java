package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.domain.VideoDO;
import com.gutongxue.wxapp.domain.VideoVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {
    @Insert("insert into `gtx_base_video` (`user_openid`,`gmt_create`,`gmt_modified`,`video_url`,`video_cover`,`video_description`,`video_source`,`video_status`)\n" +
            " values(#{video.openid},#{video.createTime},#{video.modifiedTime},#{video.url},#{video.cover},#{video.description},#{video.source},#{video.status})")
    @Options(useGeneratedKeys = true,keyProperty = "video.id")
    void insertVideo(@Param("video")VideoDO videoDO);

    @Results({
            @Result(property = "id" , column = "id"),
            @Result(property = "createTime" , column = "gmt_create"),
            @Result(property = "modifiedTime" , column = "gmt_modified"),
            @Result(property = "url" , column = "video_url"),
            @Result(property = "cover" , column = "video_cover"),
            @Result(property = "description" , column = "video_description"),
            @Result(property = "source" , column = "video_source"),
            @Result(property = "status" , column = "video_status"),
            @Result(property = "user" , column = "user_openid" , one = @One(select = "com.gutongxue.wxapp.dao.UserMapper.getUser"))
    })
    @SelectProvider(type = VideoProvider.class , method = "queryByParam")
    List<VideoVO> listVideo(@Param("param")QueryParam queryParam);

    @SelectProvider(type = VideoProvider.class , method = "queryCountByParam")
    int countVideo(@Param("param")QueryParam queryParam);

    @Select("select count(*) from gtx_base_video where video_description = #{0}")
    int countVideoByDescription(String description);
}
