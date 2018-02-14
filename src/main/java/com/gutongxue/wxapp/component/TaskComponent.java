package com.gutongxue.wxapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author Created by ShadowSaint on 2018/2/14
 */
@Component
public class TaskComponent {
    @Autowired
    RedisComponent redisComponent;

    @Async
    public void resetRedis(){
        redisComponent.clearRedis();
        redisComponent.initRedis();
    }
}
