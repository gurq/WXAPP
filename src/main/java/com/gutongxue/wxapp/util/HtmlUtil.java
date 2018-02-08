package com.gutongxue.wxapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by Shadow on 2016/11/2.
 */
public class HtmlUtil {

    //unicode转中文
    public static String convertUnicode(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    //发送get方法
    public static String sendGet(String inputUrl, String charset) {
        StringBuffer html = new StringBuffer();
        // 服务器的域名
        try {
            URL url = new URL(inputUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            // 设置请求头参数

            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Cookie", "JSESSIONID=7085B494C82441403B3A5CD06352913C.www1csres; ReadCookie0=\"\"; ReadCookie1=\"\"; ReadCookie2=\"\"; ReadCookie3=\"\"; ReadCookie4=%3Ca+href%3D%27%2Fdetail%2F283766.html%27+title%3D%27%A1%B6%B9%AB%C2%B7%CF%EE%C4%BF%B0%B2%C8%AB%D0%D4%C6%C0%BC%DB%B9%E6%B7%B6%A1%B7%CA%CD%D2%E5%CA%D6%B2%E1+JTG+B05-2015+978-7-114-12865-3%27+target%3D%27_blank%27%3E%A1%B6%B9%AB%C2%B7%CF%EE%C4%BF%B0%B2%C8%AB%D0%D4%C6%C0%BC%DB%B9%E6%B7%B6%A1%B7%CA%CD%D2%E5%CA%D6%B2%E1+..%3C%2Fa%3E; ReadCookie5=%3Ca+href%3D%27%2Fdetail%2F174958.html%27+title%3D%27%B1%EA%D7%BC%BB%AF%B9%A4%D7%F7%B5%BC%D4%F2.++%B5%DA1%B5%A5%3A%B1%EA%D7%BC%B5%C4%C6%F0%B2%DD%D3%EB%B1%ED%CA%F6%B9%E6%D4%F2.++%B5%DA1%B2%BF%B7%D6%3A%B1%EA%D7%BC%B1%E0%D0%B4%B5%C4%BB%F9%B1%BE%B9%E6%B6%A8+GB%2FT+1.1-1993%27+target%3D%27_blank%27%3E%B1%EA%D7%BC%BB%AF%B9%A4%D7%F7%B5%BC%D4%F2.++%B5%DA1%B5%A5%3A%B1%EA%D7%BC%B5%C4%C6%F0..%3C%2Fa%3E; _gat=1; _ga=GA1.2.1677987453.1478160105");
            conn.setRequestProperty("Host", "www.csres.com");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");


            // 将参数头的数据写入到输出流中

            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), charset);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }

    //发送get方法
    public static String sendGetGzip(String inputUrl, String charset) {
        StringBuffer html = new StringBuffer();
        // 服务器的域名
        try {
            URL url = new URL(inputUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            // 设置请求头参数

            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Cookie", "JSESSIONID=7085B494C82441403B3A5CD06352913C.www1csres; ReadCookie0=\"\"; ReadCookie1=\"\"; ReadCookie2=\"\"; ReadCookie3=\"\"; ReadCookie4=%3Ca+href%3D%27%2Fdetail%2F283766.html%27+title%3D%27%A1%B6%B9%AB%C2%B7%CF%EE%C4%BF%B0%B2%C8%AB%D0%D4%C6%C0%BC%DB%B9%E6%B7%B6%A1%B7%CA%CD%D2%E5%CA%D6%B2%E1+JTG+B05-2015+978-7-114-12865-3%27+target%3D%27_blank%27%3E%A1%B6%B9%AB%C2%B7%CF%EE%C4%BF%B0%B2%C8%AB%D0%D4%C6%C0%BC%DB%B9%E6%B7%B6%A1%B7%CA%CD%D2%E5%CA%D6%B2%E1+..%3C%2Fa%3E; ReadCookie5=%3Ca+href%3D%27%2Fdetail%2F174958.html%27+title%3D%27%B1%EA%D7%BC%BB%AF%B9%A4%D7%F7%B5%BC%D4%F2.++%B5%DA1%B5%A5%3A%B1%EA%D7%BC%B5%C4%C6%F0%B2%DD%D3%EB%B1%ED%CA%F6%B9%E6%D4%F2.++%B5%DA1%B2%BF%B7%D6%3A%B1%EA%D7%BC%B1%E0%D0%B4%B5%C4%BB%F9%B1%BE%B9%E6%B6%A8+GB%2FT+1.1-1993%27+target%3D%27_blank%27%3E%B1%EA%D7%BC%BB%AF%B9%A4%D7%F7%B5%BC%D4%F2.++%B5%DA1%B5%A5%3A%B1%EA%D7%BC%B5%C4%C6%F0..%3C%2Fa%3E; _gat=1; _ga=GA1.2.1677987453.1478160105");
            conn.setRequestProperty("Host", "www.csres.com");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");


            // 将参数头的数据写入到输出流中

            InputStream bis = new GZIPInputStream(conn.getInputStream());
            InputStreamReader isr = new InputStreamReader(bis, charset);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }

    //发送 http post请求
    public static String sendPost(String inputUrl, String param, String charset) {
        StringBuffer html = new StringBuffer();
        BufferedReader reader = null;
        // 服务器的域名
        try {
            URL url = new URL(inputUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            // 设置请求头参数

            String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
            //conn.setRequestProperty("Cookie", "ASP.NET_SessionId=m0qjd2ciw3mpyggr0w10roqq; Hm_lvt_5cb722868e76fb83c8216993fb5e8d5a=1466579697; Hm_lpvt_5cb722868e76fb83c8216993fb5e8d5a=1466581752");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Charset", "UTF-8");

            StringBuilder sb = new StringBuilder();
            sb.append(param);
            conn.getOutputStream().write(param.getBytes(charset));
            // 将参数头的数据写入到输出流中

            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), charset);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }

    public static String sendPostGzip(String inputUrl, String param, String charset) {
        StringBuffer html = new StringBuffer();
        // 服务器的域名
        try {
            URL url = new URL(inputUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            // 设置请求头参数

            String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
            //conn.setRequestProperty("Cookie", "ASP.NET_SessionId=m0qjd2ciw3mpyggr0w10roqq; Hm_lvt_5cb722868e76fb83c8216993fb5e8d5a=1466579697; Hm_lpvt_5cb722868e76fb83c8216993fb5e8d5a=1466581752");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Charset", "UTF-8");

            StringBuilder sb = new StringBuilder();
            sb.append(param);
            conn.getOutputStream().write(param.getBytes(charset));
            // 将参数头的数据写入到输出流中

            InputStream bis = new GZIPInputStream(conn.getInputStream());
            InputStreamReader isr = new InputStreamReader(bis, charset);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                html.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }

}
