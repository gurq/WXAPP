package com.gutongxue.wxapp.domain;

import com.gutongxue.wxapp.util.GRQUtil;

public class VideoDO {
    private int id;
    private String openid;
    private String createTime;
    private String modifiedTime;
    private String url;
    private String cover;
    private String description;
    private int source;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        if (!GRQUtil.checkNull(modifiedTime)){
            modifiedTime=modifiedTime.split("\\.")[0];
        }
        this.modifiedTime = modifiedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
