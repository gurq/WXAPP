package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.Result;
import com.gutongxue.wxapp.domain.VideoDO;
import com.gutongxue.wxapp.service.VideoService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/video/list",method = RequestMethod.GET)
    public Result getVideoList(HttpServletRequest request){
        Result result=new Result();
        try {
            int page= GRQUtil.getRequestInteger(request,"page",0);
            int size=GRQUtil.getRequestInteger(request,"size",5);
            List<VideoDO> list=videoService.listVideo(page,size);
            int count=videoService.countVideo();

            JSONObject resultJO=new JSONObject();
            resultJO.put("list",list);
            resultJO.put("count",count);
            result.setData(resultJO);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }
}
