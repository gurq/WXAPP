package com.gutongxue.wxapp.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.VideoDO;
import com.gutongxue.wxapp.service.VideoService;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Shadow on 2016/12/22.
 */
@Component
public class NeiHanDuanZiCrawler {
    //http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-104&min_time=1482410275
    //http://ic.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&count=30&min_time=1482409205&screen_width=1080&iid=6856958363&device_id=34666038486&ac=wifi&channel=baidu&aid=7&app_name=joke_essay&version_code=580&version_name=5.8.0&device_platform=android&ssmix=a&device_type=ZUK+Z2121&device_brand=ZUK&os_api=23&os_version=6.0.1&uuid=861305030160585&openudid=74978bd8655e44fd&manifest_version_code=580&resolution=1080*1920&dpi=480&update_version_code=5804
    @Autowired
    VideoService videoService;

    public int getNeiHanDuanZiInfo() {
        int count = 0;

        int page = 0;
        while (count < 40) {
            if (page > 100) {
                break;
            }
            try {
                Instant instant=Instant.now();
                String sourceUrl="http://ic.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&count=30&min_time="+instant.getEpochSecond()+"&screen_width=1080&iid=6856958363&device_id=34666038486&ac=wifi&channel=baidu&aid=7&app_name=joke_essay&version_code=580&version_name=5.8.0&device_platform=android&ssmix=a&device_type=ZUK+Z2121&device_brand=ZUK&os_api=23&os_version=6.0.1&uuid=861305030160585&openudid=74978bd8655e44fd&manifest_version_code=580&resolution=1080*1920&dpi=480&update_version_code=5804";
                JSONObject apiJsonObject= JSON.parseObject(HtmlUtil.sendGetGzip(sourceUrl,"utf-8"));
                apiJsonObject=apiJsonObject.getJSONObject("data");
                JSONArray listJsonArray=apiJsonObject.getJSONArray("data");
                if (listJsonArray.size()==0){
                    break;
                }
                for (Object o:listJsonArray){
                    JSONObject itemJsonObject= (JSONObject) o;
                    if (itemJsonObject.get("ad")!=null){
                        //广告
                        continue;
                    }
                    itemJsonObject=itemJsonObject.getJSONObject("group");
                    if (itemJsonObject.get("720p_video")==null){
                        //没有720p视频不抓
                        continue;
                    }

                    JSONObject coverJsonObject=itemJsonObject.getJSONObject("large_cover");
                    JSONArray coverJsonArray=coverJsonObject.getJSONArray("url_list");
                    JSONObject firstCoverJsonObject=coverJsonArray.getJSONObject(0);
                    JSONObject videoJsonObject=itemJsonObject.getJSONObject("720p_video");
                    JSONArray videoJsonArray=videoJsonObject.getJSONArray("url_list");
                    JSONObject firstVideoJsonObject=videoJsonArray.getJSONObject(0);

                    VideoDO videoDO=new VideoDO();
                    videoDO.setOpenid("oq0IY0UMBteouLiYS_-3RkXtYRfk");
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    videoDO.setCreateTime(now);
                    videoDO.setModifiedTime(now);
                    videoDO.setUrl(firstVideoJsonObject.getString("url"));
                    videoDO.setCover(firstCoverJsonObject.getString("url"));
                    videoDO.setDescription(itemJsonObject.getString("text"));
                    videoDO.setSource(1);
                    videoDO.setStatus(1);
                    int countVideo=videoService.countVideoByDescription(videoDO.getDescription());
                    if (countVideo==0){
                        videoService.insertVideo(videoDO);
                        count++;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            page++;
        }
        count-=videoService.deleteRepeatVideo();
        return count;
    }
}
