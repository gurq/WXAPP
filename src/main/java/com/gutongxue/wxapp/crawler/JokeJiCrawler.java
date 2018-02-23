package com.gutongxue.wxapp.crawler;

import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.service.JokeService;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class JokeJiCrawler {
    @Autowired
    JokeService jokeService;

    /**
     * 每天凌晨0点30执行,抓取昨天的内容,
     * 如果内容为图片,存入图片库,如果内容为笑话,存入笑话库
     */
    public int getJokeJiInfo() {
        int count = 0;
        String yesterdayYearMonth = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy_M"));
        String jokeListUrl = "http://www.jokeji.cn/DateUpdate_" + yesterdayYearMonth + ".htm";
        String jokeListUrlHtml = HtmlUtil.sendGetGzip(jokeListUrl, "gbk");
        if (GRQUtil.checkNull(jokeListUrlHtml)) {
            return 0;
        }
        Document jokeListDoc = Jsoup.parse(jokeListUrlHtml);
        Elements jokeListElements = jokeListDoc.select("tbody tbody tbody tr");
        String yesterdayYearMonthDay = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String jokeUrl="";
        for (Element element : jokeListElements) {
            try {
                Elements dateElements = element.select("span.date");
                if (dateElements.size() == 0) {
                    continue;
                }
                String date = dateElements.first().text().trim();
                if (date == null || !date.equals(yesterdayYearMonthDay)) {
                    continue;
                }
                jokeUrl = element.select("a").first().attr("href").trim();
                jokeUrl = "http://www.jokeji.cn" + jokeUrl;
                String jokeUrlHtml = HtmlUtil.sendGetGzip(jokeUrl, "gbk");
                Document jokeDoc = Jsoup.parse(jokeUrlHtml);
                Elements jokeElements = jokeDoc.select("p");
                String jokeContent = jokeElements.toString();
                if (jokeContent != null && !jokeContent.equals("")) {
                    JokeDO jokeDO = new JokeDO();
                    jokeDO.setOpenid("oq0IY0UMBteouLiYS_-3RkXtYRfk");
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    jokeDO.setCreateTime(now);
                    jokeDO.setModifiedTime(now);
                    jokeDO.setContent(jokeContent);
                    jokeDO.setSource(1);
                    jokeDO.setStatus(1);
                    int contentCount=jokeService.countJokeByContent(jokeContent);
                    if (contentCount > 0) {
                        continue;
                    }
                    jokeService.insertJoke(jokeDO);
                    count++;
                }
            } catch (Exception e) {
                continue;
            }
        }
        count-=jokeService.deleteRepeatJoke();
        return count;
    }

}
