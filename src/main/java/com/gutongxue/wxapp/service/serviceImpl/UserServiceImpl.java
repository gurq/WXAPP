package com.gutongxue.wxapp.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gutongxue.wxapp.dao.UserMapper;
import com.gutongxue.wxapp.domain.UserDO;
import com.gutongxue.wxapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public void insertUser(UserDO userDO) {
        userMapper.insertUser(userDO);
    }

    @Override
    public void deleteUser(String openid) {
        userMapper.deleteUser(openid);
    }

    @Override
    public void updateUser(UserDO userDO) {
        userMapper.updateUser(userDO);
    }

    @Override
    public List<UserDO> listUser(int pageNum,int sizeNum) {
        //分页插件: 查询第1页，每页10行
        Page<UserDO> page = PageHelper.startPage(pageNum, sizeNum);
        userMapper.listUser();
        //数据表的总行数
        page.getTotal();
        //分页查询结果的总行数
        page.size();
        //第一个User对象，参考list，序号0是第一个元素，依此类推
        page.get(0);
        return page;
    }

    @Override
    public int countUser() {
        return userMapper.countUser();
    }

    @Override
    public UserDO getUser(String openid) {
        return userMapper.getUser(openid);
    }

    @Override
    public int countUserByOpenid(String openid) {
        return userMapper.countUserByOpenid(openid);
    }
}
