package com.gutongxue.wxapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtil {
    public static void main(String[] args) {
        String url="http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42";
        String html=HtmlUtil.sendGetGzip(url,"utf-8");
        System.out.println(html);
    }
}
