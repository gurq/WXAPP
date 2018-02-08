package com.gutongxue.wxapp.domain;

import com.gutongxue.wxapp.util.GRQUtil;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
public class JokeVO {
    private int id;
    private UserDO user;
    private String createTime;
    private String modifiedTime;
    private String content;
    private int source;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
