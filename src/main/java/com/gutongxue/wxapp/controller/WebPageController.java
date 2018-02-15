package com.gutongxue.wxapp.controller;

import com.gutongxue.wxapp.domain.ImageVO;
import com.gutongxue.wxapp.domain.JokeVO;
import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.domain.VideoVO;
import com.gutongxue.wxapp.service.ImageService;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.service.VideoService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/14
 */
@Controller
public class WebPageController {
    @Autowired
    JokeService jokeService;
    @Autowired
    ImageService imageService;
    @Autowired
    VideoService videoService;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String printIndexPage(ModelMap modelMap){
        return "redirect:/j";
    }

    @RequestMapping(value = "/j",method = RequestMethod.GET)
    public String printJokePage(ModelMap modelMap, HttpServletRequest request){
        List<JokeVO> list=new ArrayList<>();
        int lastPage=-1;
        int nextPage=-1;
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

            String redisKey="jokeList"+queryParam.toString();
            if (redisTemplate.opsForValue().get(redisKey)==null){
                list=jokeService.listJoke(queryParam);
                redisTemplate.opsForValue().set(redisKey,list);
            }else {
                list= (List<JokeVO>) redisTemplate.opsForValue().get(redisKey);
            }

            for (JokeVO joke:list){
                joke.setContent(joke.getContent().replace("\n\r","<br/>"));
            }

            int count;
            String redisCountKey="jokeListCount"+queryParam.toString();
            if (redisTemplate.opsForValue().get(redisCountKey)==null){
                count=jokeService.countJoke(queryParam);
                redisTemplate.opsForValue().set(redisCountKey,count);
            }else {
                count= (int) redisTemplate.opsForValue().get(redisCountKey);
            }

            if (page>1){
                lastPage=page-1;
            }
            if (count-(page*size)>0){
                nextPage=page+1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("lastPage",lastPage);
        modelMap.addAttribute("nextPage",nextPage);

        return "joke";
    }

    @RequestMapping(value = "/i",method = RequestMethod.GET)
    public String printImagePage(ModelMap modelMap,HttpServletRequest request){
        List<ImageVO> list=new ArrayList<>();
        int lastPage=-1;
        int nextPage=-1;
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

            String redisKey="imageList"+queryParam.toString();
            if (redisTemplate.opsForValue().get(redisKey)==null){
                list=imageService.listImage(queryParam);
                redisTemplate.opsForValue().set(redisKey,list);
            }else {
                list= (List<ImageVO>) redisTemplate.opsForValue().get(redisKey);
            }

            int count;
            String redisCountKey="imageListCount"+queryParam.toString();
            if (redisTemplate.opsForValue().get(redisCountKey)==null){
                count=jokeService.countJoke(queryParam);
                redisTemplate.opsForValue().set(redisCountKey,count);
            }else {
                count= (int) redisTemplate.opsForValue().get(redisCountKey);
            }

            if (page>1){
                lastPage=page-1;
            }
            if (count-(page*size)>0){
                nextPage=page+1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("lastPage",lastPage);
        modelMap.addAttribute("nextPage",nextPage);
        return "image";
    }

    @RequestMapping(value = "/v",method = RequestMethod.GET)
    public String printVideoPage(ModelMap modelMap,HttpServletRequest request){
        List<VideoVO> list=new ArrayList<>();
        int lastPage=-1;
        int nextPage=-1;
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
            if (redisTemplate.opsForValue().get(redisKey)==null){
                list=videoService.listVideo(queryParam);
                redisTemplate.opsForValue().set(redisKey,list);
            }else {
                list= (List<VideoVO>) redisTemplate.opsForValue().get(redisKey);
            }

            int count;
            String redisCountKey="videoListCount"+queryParam.toString();
            if (redisTemplate.opsForValue().get(redisCountKey)==null){
                count=jokeService.countJoke(queryParam);
                redisTemplate.opsForValue().set(redisCountKey,count);
            }else {
                count= (int) redisTemplate.opsForValue().get(redisCountKey);
            }

            if (page>1){
                lastPage=page-1;
            }
            if (count-(page*size)>0){
                nextPage=page+1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("lastPage",lastPage);
        modelMap.addAttribute("nextPage",nextPage);
        return "video";
    }
}
