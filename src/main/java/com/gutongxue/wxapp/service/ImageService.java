package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.ImageDO;

import java.util.List;

public interface ImageService {
    int countImageByDescription(String description);
    void insertImage(ImageDO imageDO);
    void deleteImage(int id);
    void updateImageStatus(int id,int status,String now);
    List<ImageDO> listImage(int pageNum,int sizeNum);
    int countImage();
}