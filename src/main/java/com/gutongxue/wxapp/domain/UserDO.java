package com.gutongxue.wxapp.domain;

import com.gutongxue.wxapp.util.GRQUtil;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
public class UserDO {
    private String openid;
    private String createTime;
    private String loginTime;
    private String nickname;
    private Integer gender;
    private String avatar;
    private String ip;
    private String country;
    private String region;
    private String city;
    private String isp;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        if (!GRQUtil.checkNull(createTime)){
            createTime=createTime.split("\\.")[0];
        }
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        if (!GRQUtil.checkNull(loginTime)){
            loginTime=loginTime.split("\\.")[0];
        }
        this.loginTime = loginTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
