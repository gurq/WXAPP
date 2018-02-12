package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.*;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class JokeController {
    @Autowired
    JokeService jokeService;

    @RequestMapping(value = "/joke/list",method = RequestMethod.GET)
    public Result getJokeList(HttpServletRequest request){
        Result result=new Result();
        try {
            int page= GRQUtil.getRequestInteger(request,"page",1);
            int size=GRQUtil.getRequestInteger(request,"size",5);
            int status=GRQUtil.getRequestInteger(request,"status",1);
            String openid=request.getParameter("openid");
            QueryParam queryParam =new QueryParam();
            queryParam.setPage(page);
            queryParam.setSize(size);
            queryParam.setStatus(status);
            queryParam.setOpenid(openid);

            List<JokeVO> list=jokeService.listJoke(queryParam);
            int count=jokeService.countJoke();

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

    @RequestMapping(value = "/joke",method = RequestMethod.POST)
    public Result postJoke(HttpServletRequest request){
        Result result=new Result();
        try {
            String openid=request.getParameter("openid");
            String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String content=request.getParameter("content");

            JokeDO jokeDO=new JokeDO();
            jokeDO.setOpenid(openid);
            jokeDO.setCreateTime(now);
            jokeDO.setModifiedTime(now);
            jokeDO.setContent(content);
            jokeDO.setSource(0);
            jokeDO.setStatus(1);

            jokeService.insertJoke(jokeDO);

            result.setData(jokeDO);

        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping(value = "/joke",method = RequestMethod.DELETE)
    public Result deleteJoke(HttpServletRequest request){
        Result result=new Result();
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            jokeService.deleteJoke(id);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping(value = "/joke",method = RequestMethod.OPTIONS)
    public Result passJoke(HttpServletRequest request){
        Result result=new Result();
        try {
            int id = Integer.valueOf(request.getParameter("id"));
            int status = Integer.valueOf(request.getParameter("status"));
            String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            jokeService.updateJokeStatus(id,status,now);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping("/")
    public String index(){
        return "启动成功";
    }

}
