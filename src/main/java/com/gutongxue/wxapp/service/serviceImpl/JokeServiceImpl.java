package com.gutongxue.wxapp.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gutongxue.wxapp.dao.MybatisDao;
import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeServiceImpl implements JokeService {
    @Autowired
    MybatisDao mybatisDao;

    public List<JokeDO> listJoke(int pageNum, int sizeNum){
        //分页插件: 查询第1页，每页10行
        Page<JokeDO> page = PageHelper.startPage(pageNum, sizeNum);
        mybatisDao.listJoke();
        //数据表的总行数
        page.getTotal();
        //分页查询结果的总行数
        page.size();
        //第一个User对象，参考list，序号0是第一个元素，依此类推
        page.get(0);
        return page;
    }
}
