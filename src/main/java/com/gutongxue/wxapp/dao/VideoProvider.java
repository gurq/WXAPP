package com.gutongxue.wxapp.dao;

import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.util.GRQUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @Author Created by ShadowSaint on 2018/2/12
 */
public class VideoProvider {
    private final String TABLE = "gtx_base_video";

    public String queryByParam(Map<String, Object> para){
        QueryParam queryParam = (QueryParam) para.get("param");
        SQL sql=new SQL().SELECT("*").FROM(TABLE).WHERE("video_status = "+ queryParam.getStatus());
        if (!GRQUtil.checkNull(queryParam.getOpenid())){
            sql.WHERE("user_openid = #{param.openid}");
        }
        sql.ORDER_BY("gmt_modified desc");
        return sql.toString();
    }
}
