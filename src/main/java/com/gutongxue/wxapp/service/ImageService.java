package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.ImageVO;
import com.gutongxue.wxapp.domain.QueryParam;

import java.util.List;

public interface ImageService {
    int countImageByDescription(String description);
    void insertImage(ImageDO imageDO);
    void deleteImage(int id);
    void updateImageStatus(int id,int status,String now);
    List<ImageVO> listImage(QueryParam queryParam);
    int countImage(QueryParam queryParam);
}