package com.gutongxue.wxapp.controller;

import com.gutongxue.wxapp.component.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Created by ShadowSaint on 2018/2/15
 */
@RestController
public class RedisController {
    @Autowired
    RedisComponent redisComponent;

    @RequestMapping("/redis/clear")
    public String clear(){
        redisComponent.clearRedis();
        return "成功";
    }

    @RequestMapping("/redis/init")
    public String init(){
        redisComponent.initRedis();
        return "成功";
    }
}
