package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.Result;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class WxController {
    private final String APPIP = "wx7f740da9671cad5a";
    private final String APPSECRET = "ac661e05f1a1114110fe31df18fbb023";

    @RequestMapping("/wx/unionid")
    public Result getWxUnionId(HttpServletRequest request) {
        Result result = new Result();
        try {
            String code = request.getParameter("code");
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPIP + "&secret=" + APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
            String html = HtmlUtil.sendGet(url, "utf-8");
            JSONObject jo = JSON.parseObject(html);
            if (GRQUtil.checkNull(jo.getString("errmsg"))) {
                result.setData(jo);
            } else {
                result.setMessage(jo.getString("errmsg"));
                result.setStatus(false);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping("/wx/access_token")
    public Result getAccessToken(HttpServletRequest request) {
        Result result = new Result();
        try {
            //先看一眼全局变量里有没有
            String token = (String) request.getServletContext().getAttribute("access_token");
            String expiresString = (String) request.getServletContext().getAttribute("expires_time");
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime expiresTime;
            //如果全局变量里没有的话,要从新取
            if (GRQUtil.checkNull(token)) {
                result = getNewWxToken(result, request);
            } else {
                //如果全局变量里面有的话,要判断过期了没
                expiresTime = LocalDateTime.parse(expiresString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (nowTime.isBefore(expiresTime)) {
                    JSONObject resultJO = new JSONObject();
                    resultJO.put("access_token", token);
                    resultJO.put("expires_time", expiresString);
                    result.setData(resultJO);
                    result.setMessage("请求成功,直接从全局变量里取的值,没有请求微信API");
                } else {
                    result = getNewWxToken(result, request);
                }
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    private Result getNewWxToken(Result result, HttpServletRequest request) {
        JSONObject jo = getWxTokenApi();
        String token = jo.getString("access_token");
        //获取成功了
        if (!GRQUtil.checkNull(token)) {
            long expiresIn = jo.getLong("expires_in");
            LocalDateTime expiresTime = LocalDateTime.now().plusSeconds(expiresIn - 60);
            String expiresTimeString = expiresTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            request.getServletContext().setAttribute("access_token", token);
            request.getServletContext().setAttribute("expires_time", expiresTimeString);
            JSONObject resultJO = new JSONObject();
            resultJO.put("access_token", token);
            resultJO.put("expires_time", expiresTimeString);
            result.setData(resultJO);
            result.setMessage("请求成功,从微信API新请求了一个access_token");
        } else {
            //获取没成功
            String errmsg = jo.getString("errmsg");
            //看上一步的网络请求成功了没
            if (!GRQUtil.checkNull(errmsg)) {
                result.setStatus(false);
                result.setMessage("errcode:" + jo.get("errcode") + "    errmsg:" + errmsg);
            } else {
                result.setStatus(false);
                result.setMessage("getWxTokenApi()接口返回数据为空,可能是向微信请求token时网络超时");
            }
        }
        return result;
    }

    private JSONObject getWxTokenApi() {
        JSONObject jo = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPIP + "&secret=" + APPSECRET;
        String html = HtmlUtil.sendGet(url, "utf-8");
        if (!GRQUtil.checkNull(html)) {
            jo = JSON.parseObject(html);
        }
        return jo;
    }
}
