package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.component.TaskComponent;
import com.gutongxue.wxapp.domain.*;
import com.gutongxue.wxapp.service.VideoService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
@RestController
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TaskComponent taskComponent;

    @RequestMapping(value = "/video/list",method = RequestMethod.GET)
    public Result getVideoList(HttpServletRequest request){
        Result result=new Result();
        try {
            int page= GRQUtil.getRequestInteger(request,"page",1);
//            int size=GRQUtil.getRequestInteger(request,"size",5);
            int size=3;
            int status=GRQUtil.getRequestInteger(request,"status",1);
            String openid=request.getParameter("openid");
            QueryParam queryParam =new QueryParam();
            queryParam.setPage(page);
            queryParam.setSize(size);
            queryParam.setStatus(status);
            queryParam.setOpenid(openid);

            String redisKey="videoList"+queryParam.toString();
            List<VideoVO> list;
            if (redisTemplate.opsForValue().get(redisKey)==null){
                list=videoService.listVideo(queryParam);
                redisTemplate.opsForValue().set(redisKey,list);
            }else {
                list= (List<VideoVO>) redisTemplate.opsForValue().get(redisKey);
            }
//            int count=videoService.countVideo();

            JSONObject resultJO=new JSONObject();
            resultJO.put("list",list);
//            resultJO.put("count",count);
            result.setData(resultJO);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }
}
