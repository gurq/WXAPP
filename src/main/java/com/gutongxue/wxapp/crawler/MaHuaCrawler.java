package com.gutongxue.wxapp.crawler;

import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.service.ImageService;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Shadow on 2016/11/15.
 */
@Component
public class MaHuaCrawler {
    @Autowired
    ImageService imageService;
    @Autowired
    JokeService jokeService;

    /**
     * 每天凌晨0点45执行
     */
    public int getMaHuaInfo(){
        int count=0;
        try {
            String today= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy--MM-dd"));
            String yesterday=LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String sourceUrl="http://www.mahua.com/";
            String sourceHtml= HtmlUtil.sendGetGzip(sourceUrl,"utf-8");
            String url= Jsoup.parse(sourceHtml).select("span.joke-title").first().select("a").first().attr("href").trim();
            String html=HtmlUtil.sendGetGzip(url,"utf-8");
            Document document=Jsoup.parse(html);
            String time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
            while (!GRQUtil.checkNull(time)&&((time.equals(today)||time.equals(yesterday)))){
                Elements imgElements=document.select("div.joke-content img");
                //如果是今天的话,跳过这一条
                if (time.equals(today)){
                    url=document.select("div.joke-content").first().attr("onclick").replace("javascript:location.href='","").replace("'","");
                    html=HtmlUtil.sendGetGzip(url,"utf-8");
                    document=Jsoup.parse(html);
                    time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
                    continue;
                }
                if (imgElements.size()>0){
                    String description=document.select("h1.joke-title").first().text().trim();
                    ImageDO imageDO=new ImageDO();
                    imageDO.setOpenid("oq0IY0UMBteouLiYS_-3RkXtYRfk");
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    imageDO.setCreateTime(now);
                    imageDO.setModifiedTime(now);
                    imageDO.setUrl(imgElements.first().attr("src"));
                    imageDO.setDescription(description);
                    imageDO.setSource(1);
                    imageDO.setStatus(1);
                    int imageCount = imageService.countImageByDescription(imageDO.getDescription());
                    if (imageCount==0){
                        imageService.insertImage(imageDO);
                        count++;
                    }
                }else {
                    String content=document.select("div.joke-content").html().trim();
                    JokeDO jokeDO = new JokeDO();
                    jokeDO.setOpenid("oq0IY0UMBteouLiYS_-3RkXtYRfk");
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    jokeDO.setCreateTime(now);
                    jokeDO.setModifiedTime(now);
                    jokeDO.setContent(content);
                    jokeDO.setSource(1);
                    jokeDO.setStatus(1);
                    int contentCount=jokeService.countJokeByContent(content);
                    if (contentCount == 0) {
                        jokeService.insertJoke(jokeDO);
                        count++;
                    }
                }
                String nextUrl=document.select("div.joke-content").first().attr("onclick").replace("javascript:location.href='","").replace("'","");
                if (nextUrl.equals(url)){
                    break;
                }else {
                    url=nextUrl;
                }
                html=HtmlUtil.sendGetGzip(url,"utf-8");
                document=Jsoup.parse(html);
                time=document.select("p[class=\"joke-uname\"] span").first().text().trim().split(" ")[0];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
