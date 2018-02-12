package com.gutongxue.wxapp.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gutongxue.wxapp.dao.VideoMapper;
import com.gutongxue.wxapp.domain.ImageDO;
import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.domain.VideoDO;
import com.gutongxue.wxapp.domain.VideoVO;
import com.gutongxue.wxapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Override
    public void insertVideo(VideoDO videoDO) {
        videoMapper.insertVideo(videoDO);
    }

    @Override
    public List<VideoVO> listVideo(QueryParam queryParam) {
        //分页插件: 查询第1页，每页10行
        Page<VideoVO> page = PageHelper.startPage(queryParam.getPage(), queryParam.getSize());
        videoMapper.listVideo(queryParam);
        //数据表的总行数
//        page.getTotal();
        //分页查询结果的总行数
//        page.size();
        //第一个User对象，参考list，序号0是第一个元素，依此类推
//        page.get(0);
        return page;
    }

    @Override
    public int countVideo() {
        return videoMapper.countVideo();
    }

    @Override
    public int countVideoByDescription(String description) {
        return videoMapper.countVideoByDescription(description);
    }
}
