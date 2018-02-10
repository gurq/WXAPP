package com.gutongxue.wxapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gutongxue.wxapp.domain.Result;
import com.gutongxue.wxapp.domain.UserDO;
import com.gutongxue.wxapp.service.UserService;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user" , method = RequestMethod.POST)
    public Result postUser(HttpServletRequest request){
        Result result=new Result();
        try {
            UserDO userDO=getUserDO(request);
            int count=userService.countUserByOpenid(userDO.getOpenid());
            if (count==0){
                userService.insertUser(userDO);
            }else {
                userService.updateUser(userDO);
            }
            result.setData(userDO);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    private static UserDO getUserDO(HttpServletRequest request){
        String openid=request.getParameter("openid");
        String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String nickname=request.getParameter("nickname");
        String avatar=request.getParameter("avatar");
        int gender=GRQUtil.getRequestInteger(request,"gender",1);
        String ip= GRQUtil.getIpAddr(request);

        String url="http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
        String html= HtmlUtil.sendGetGzip(url,"utf-8");

        JSONObject ipJO= JSON.parseObject(html);
        JSONObject ipDataJO=ipJO.getJSONObject("data");
        String country=ipDataJO.getString("country");
        String region=ipDataJO.getString("region");
        String city=ipDataJO.getString("city");
        String isp=ipDataJO.getString("isp");

        UserDO userDO=new UserDO();
        userDO.setOpenid(openid);
        userDO.setCreateTime(now);
        userDO.setLoginTime(now);
        userDO.setNickname(nickname);
        userDO.setAvatar(avatar);
        userDO.setGender(gender);
        userDO.setIp(ip);
        userDO.setCountry(country);
        userDO.setRegion(region);
        userDO.setCity(city);
        userDO.setIsp(isp);

        return userDO;
    }

    @RequestMapping(value = "/user" , method = RequestMethod.DELETE)
    public Result deleteUser(HttpServletRequest request){
        Result result=new Result();
        try {
            String openid=request.getParameter("openid");
            userService.deleteUser(openid);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }

    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public Result getUser(HttpServletRequest request){
        Result result=new Result();
        try {
            String openid=request.getParameter("openid");
            UserDO userDO=userService.getUser(openid);
            result.setData(userDO);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }


}
