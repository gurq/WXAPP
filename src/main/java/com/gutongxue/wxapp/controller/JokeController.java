package com.gutongxue.wxapp.controller;

import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class JokeController {
    @Autowired
    JokeService jokeService;

    @RequestMapping("/joke")
    public List<JokeDO> joke(HttpServletRequest request){
        int page= GRQUtil.getRequestInteger(request,"page",0);
        int size=GRQUtil.getRequestInteger(request,"size",20);
        List<JokeDO> list=jokeService.listJoke(page,size);
        return list;
    }

    @RequestMapping("/")
    public String index(){
        return "启动成功";
    }

}
