package com.gutongxue.wxapp.component;

import com.gutongxue.wxapp.domain.ImageVO;
import com.gutongxue.wxapp.domain.JokeVO;
import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.domain.VideoVO;
import com.gutongxue.wxapp.service.ImageService;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/14
 */
@Component
public class RedisComponent {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    JokeService jokeService;
    @Autowired
    ImageService imageService;
    @Autowired
    VideoService videoService;

    public void initRedis(){
        QueryParam queryParam=new QueryParam();
        queryParam.setStatus(1);
        queryParam.setSize(3);
        //各缓存5页
        //第一页
        for (int i=1;i<=5;i++){
            queryParam.setPage(i);
            List<JokeVO> jokeVOList=jokeService.listJoke(queryParam);
            redisTemplate.opsForValue().set("jokeList"+queryParam.toString(),jokeVOList);
            List<ImageVO> imageVOList=imageService.listImage(queryParam);
            redisTemplate.opsForValue().set("imageList"+queryParam.toString(),imageVOList);
            List<VideoVO> videoVOList=videoService.listVideo(queryParam);
            redisTemplate.opsForValue().set("videoList"+queryParam.toString(),videoVOList);
        }
    }

    public void clearRedis(){
        redisTemplate.delete(redisTemplate.keys("*"));
    }
}
