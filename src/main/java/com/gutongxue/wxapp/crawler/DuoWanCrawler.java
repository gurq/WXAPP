package com.gutongxue.wxapp.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.KV;
import com.gutongxue.wxapp.service.ImageService;
import com.gutongxue.wxapp.service.KVService;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Shadow on 2016/11/16.
 */
@Component
public class DuoWanCrawler {
    @Autowired
    KVService kvService;
    @Autowired
    ImageService imageService;

    public int getDuoWanInfo(int type){
        int count=0;
        //获取数据库里上次抓到哪一期了
        String key;
        if (type==0){
            key="多玩今日囧图";
        }else {
            key="多玩搞笑gif";
        }
        KV kv=kvService.getV(key);
        if (GRQUtil.checkNull(kv.getV())){
            return 0;
        }
        int crawlerProgress=Integer.valueOf(kv.getV());
        String listUrl;
        if (type==0){
            listUrl="http://tu.duowan.com/tag/5037.html";
        }else {
            listUrl="http://tu.duowan.com/m/bxgif";
        }
        String listHtml= HtmlUtil.sendGetGzip(listUrl,"utf-8");
        if (GRQUtil.checkNull(listHtml)){
            listHtml= HtmlUtil.sendGet(listUrl,"utf-8");
        }
        if (GRQUtil.checkNull(listHtml)){
            return 0;
        }

        try {
            Document listDocument= Jsoup.parse(listHtml);
            Elements listElements=listDocument.select("em");
            String itemTitle="";
            String itemUrl="";
            //对列表从旧到新的遍历
            for (int i=listElements.size()-1;i>=0;i--){
                itemTitle=listElements.get(i).select("a").text().trim();
                if (GRQUtil.checkNull(itemTitle)){
                    continue;
                }
                try {
                    //截取出期数
                    if (type==0){
                        itemTitle=itemTitle.split("：")[0].split("期")[0].split("第")[1];
                    }else {
                        itemTitle=itemTitle.split("：")[0].split("弹")[0].split("第")[1];
                    }
                    int itemNumber=Integer.valueOf(itemTitle);
                    if (crawlerProgress>=itemNumber){
                        continue;
                    }
                    //获取到拿数据的url
                    itemUrl=listElements.get(i).select("a").first().attr("href").trim();
                    itemUrl=itemUrl.split("/")[itemUrl.split("/").length-1];
                    itemUrl=itemUrl.split("\\.")[0];
                    itemUrl="http://tu.duowan.com/index.php?r=show/getByGallery/&gid="+itemUrl;
                    //从url里解析出json,然后根据需要解析
                    String imgJsonObjectString=HtmlUtil.sendGetGzip(itemUrl,"utf-8");
                    if (GRQUtil.checkNull(imgJsonObjectString)){
                        imgJsonObjectString=HtmlUtil.sendGet(itemUrl,"utf-8");
                    }
                    JSONObject imgJsonObject= JSON.parseObject(imgJsonObjectString);
                    JSONArray imgJsonArray=imgJsonObject.getJSONArray("picInfo");
                    for (Object object:imgJsonArray){
                        JSONObject itemJsonObject= (JSONObject) object;
                        ImageDO imageDO=new ImageDO();
                        imageDO.setOpenid("oq0IY0UMBteouLiYS_-3RkXtYRfk");
                        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        imageDO.setCreateTime(now);
                        imageDO.setModifiedTime(now);
                        imageDO.setUrl(itemJsonObject.getString("url"));
                        imageDO.setDescription(itemJsonObject.getString("add_intro").replace("多玩",""));
                        imageDO.setSource(1);
                        imageDO.setStatus(1);
                        int imageCount = imageService.countImageByDescription(imageDO.getDescription());
                        if (imageCount>0){
                            continue;
                        }
                        imageService.insertImage(imageDO);
                        count++;
                    }
                    KV newKv =new KV();
                    newKv.setK(key);
                    newKv.setV(String.valueOf(itemNumber));
                    kvService.updateKV(newKv);
                }catch (Exception e){
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

}
