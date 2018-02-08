package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.ImageVO;

import java.util.List;

public interface ImageService {
    int countImageByDescription(String description);
    void insertImage(ImageDO imageDO);
    void deleteImage(int id);
    void updateImageStatus(int id,int status,String now);
    List<ImageVO> listImage(int pageNum, int sizeNum);
    int countImage();
}