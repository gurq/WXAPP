package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.JokeDO;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface ImageMapper {
    @Select("select count(*) from gtx_base_image where image_description = #{0}")
    int countImageByDescription(String description);

    @Insert("insert into `gtx_base_image` (`user_openid`,`gmt_create`,`gmt_modified`,`image_url`,`image_description`,`image_source`,`image_status`)\n" +
            " values(#{image.openid},#{image.createTime},#{image.modifiedTime},#{image.url},#{image.description},#{image.source},#{image.status})")
    void insertImage(@Param("image")ImageDO imageDO);

    @Delete("delete from gtx_base_image where id = #{0}")
    void deleteImage(int id);

    @Update("update gtx_base_image set image_status = #{1} , gmt_modified = #{2} where id = #{0}")
    void updateImageStatus(int id,int status,String now);

    @Results({
            @Result(property = "id" , column = "id"),
            @Result(property = "openid" , column = "user_openid"),
            @Result(property = "createTime" , column = "gmt_create"),
            @Result(property = "modifiedTime" , column = "gmt_modified"),
            @Result(property = "url" , column = "image_url"),
            @Result(property = "description" , column = "image_description"),
            @Result(property = "source" , column = "image_source"),
            @Result(property = "status" , column = "image_status")

    })
    @Select("select * from gtx_base_image where image_status = 1 order by gmt_modified desc")
    List<ImageDO> listImage();

    @Select("select count(*) from gtx_base_image where image_status = 1 ")
    int countImage();

}
