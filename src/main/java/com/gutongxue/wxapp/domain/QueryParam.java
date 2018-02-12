package com.gutongxue.wxapp.domain;

/**
 * @Author Created by ShadowSaint on 2018/2/12
 */
public class QueryParam {
    private Integer status;
    private String openid;
    private Integer page;
    private Integer size;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
