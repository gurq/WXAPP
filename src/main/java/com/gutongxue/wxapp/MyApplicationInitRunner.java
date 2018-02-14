package com.gutongxue.wxapp;

import com.gutongxue.wxapp.component.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author Created by ShadowSaint on 2018/2/14
 */
@Component
@Order(value = 1)
public class MyApplicationInitRunner implements ApplicationRunner{
    @Autowired
    RedisComponent redisComponent;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        redisComponent.initRedis();
    }
}
