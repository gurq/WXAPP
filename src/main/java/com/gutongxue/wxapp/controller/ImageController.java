package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.ImageVO;
import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.domain.Result;
import com.gutongxue.wxapp.service.ImageService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
@RestController
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/image/list",method = RequestMethod.GET)
    public Result getImageList(HttpServletRequest request){
        Result result=new Result();
        try {
            int page= GRQUtil.getRequestInteger(request,"page",1);
            int size=GRQUtil.getRequestInteger(request,"size",5);
            List<ImageVO> list=imageService.listImage(page,size);
            int count=imageService.countImage();

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

    @RequestMapping(value = "/image",method = RequestMethod.POST)
    public Result postImage(HttpServletRequest request){
        Result result=new Result();
        try {
            String openid=request.getParameter("openid");
            String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String url=request.getParameter("url");
            String description=request.getParameter("description");

            ImageDO imageDO=new ImageDO();
            imageDO.setOpenid(openid);
            imageDO.setCreateTime(now);
            imageDO.setModifiedTime(now);
            imageDO.setUrl(url);
            imageDO.setDescription(description);
            imageDO.setSource(0);
            imageDO.setStatus(0);

            imageService.insertImage(imageDO);

            result.setData(imageDO);

        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping(value = "/image",method = RequestMethod.DELETE)
    public Result deleteImage(HttpServletRequest request){
        Result result=new Result();
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            imageService.deleteImage(id);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping(value = "/image",method = RequestMethod.OPTIONS)
    public Result passImage(HttpServletRequest request){
        Result result=new Result();
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            int status = Integer.valueOf(request.getParameter("status"));
            String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            imageService.updateImageStatus(id,status,now);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }
}
