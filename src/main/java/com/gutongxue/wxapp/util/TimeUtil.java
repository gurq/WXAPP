package com.gutongxue.wxapp.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Shadow on 2016/11/15.
 */
public class TimeUtil {
    public static void main(String[] args) {

    }
    public static String getToday(){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return format;
    }
    public static String getTodayByFormat(String timeFormat){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String format = localDateTime.format(DateTimeFormatter.ofPattern(timeFormat));
        return format;
    }
    public static String getYesterdayByFormat(String timeFormat){
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
        //通过秒获取下一天日期
        long time = (date.getTime() / 1000) - 60 * 60 * 24;//秒
        date.setTime(time * 1000);//毫秒
        String yesterday = sf.format(date).toString();
        return yesterday;
    }
}
