package com.gutongxue.wxapp.util;

import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtil {
    public static void main(String[] args) {
        try {
            String url="http://tu.duowan.com/tag/5037.html";
            String html= Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).timeout(3000).get().html();
            System.out.println(html);
        }catch (Exception e){}

    }
}
