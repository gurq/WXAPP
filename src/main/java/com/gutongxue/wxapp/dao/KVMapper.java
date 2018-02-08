package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.KV;
import org.apache.ibatis.annotations.*;

@Mapper
public interface KVMapper {

    @Select("select * from gtx_k_v where k = #{k}")
    KV getV(String k);

    @Insert("insert into gtx_k_v (k,v) values (#{kv.k},#{kv.v})")
    void insertKV(@Param("kv") KV kv);

    @Update("update gtx_k_v set v = #{kv.v} where k = #{kv.k}")
    void updateKV(@Param("kv") KV kv);
}
