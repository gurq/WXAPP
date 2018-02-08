package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.VideoDO;

import java.util.List;

public interface VideoService {
    void insertVideo(VideoDO videoDO);
    List<VideoDO> listVideo(int pageNum, int sizeNum);
    int countVideo();
}
