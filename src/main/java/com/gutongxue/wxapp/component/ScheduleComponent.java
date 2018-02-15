package com.gutongxue.wxapp.component;

import com.gutongxue.wxapp.crawler.DuoWanCrawler;
import com.gutongxue.wxapp.crawler.JokeJiCrawler;
import com.gutongxue.wxapp.crawler.MaHuaCrawler;
import com.gutongxue.wxapp.crawler.NeiHanDuanZiCrawler;
import com.gutongxue.wxapp.domain.LogDO;
import com.gutongxue.wxapp.service.LogService;
import com.gutongxue.wxapp.util.GRQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

@Component
public class ScheduleComponent {
    @Autowired
    LogService logService;
    @Autowired
    DuoWanCrawler duoWanCrawler;
    @Autowired
    JokeJiCrawler jokeJiCrawler;
    @Autowired
    MaHuaCrawler maHuaCrawler;
    @Autowired
    NeiHanDuanZiCrawler neiHanDuanZiCrawler;
    @Autowired
    RedisComponent redisComponent;

//        @Scheduled(cron="0/5 * * * * ?")
    @Scheduled(cron="0 15 0 * * ?")
    public void runCrawler(){
            //避免重启项目导致脚本重新运行,所以规定,7点钟以后不准运行
            LocalDateTime localDateTime=LocalDateTime.now();
            int hour=localDateTime.getHour();
            //24小时制,放心用
            if (hour>=7){
                //按理说这个方法不应该被执行
                return;
            }
            //出于服务器性能瓶颈限制,目前暂时不打算做成异步的
            try {
                //抓开心麻花
                int mahuaCount= maHuaCrawler.getMaHuaInfo();
                Thread.sleep(1000*60);
                //内涵段子
                int neiHanDuanZiCount= neiHanDuanZiCrawler.getNeiHanDuanZiInfo();
                Thread.sleep(1000*60);
                //抓笑话集
                int jokejiCount= jokeJiCrawler.getJokeJiInfo();
                Thread.sleep(1000*60);
                //多玩爆笑gif
                int duoWanGifCount= duoWanCrawler.getDuoWanInfo(1);
                Thread.sleep(1000*60);
                //多玩每日囧图
                int duoWanImageCount= duoWanCrawler.getDuoWanInfo(0);
                //数据库记录
                LogDO logDO=new LogDO();
                logDO.setTime(GRQUtil.getTodayByFormat("yyyy-MM-dd HH:mm:ss"));
                logDO.setContent("今日脚本正常更新完毕\n" +
                        "多玩每日囧图更新数量为: "+duoWanImageCount+"\n"+
                        "多玩每日gif更新数量为: "+duoWanGifCount+"\n"+
                        "笑话集更新数量为: "+jokejiCount+"\n"+
                        "开心麻花更新数量为: "+mahuaCount+"\n"+
                        "内涵段子更新数量为:"+neiHanDuanZiCount);
                logService.insertLog(logDO);

            }catch (Exception e){
                e.printStackTrace();
            }
            redisComponent.clearRedis();
            redisComponent.initRedis();
    }
}
