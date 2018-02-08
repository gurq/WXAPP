package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.domain.UserDO;

import java.util.List;

/**
 * @Author Created by ShadowSaint on 2018/2/8
 */
public interface UserService {
    void insertUser(UserDO userDO);
    void deleteUser(String openid);
    void updateUser(UserDO userDO);
    List<UserDO> listUser(int pageNum,int sizeNum);
    int countUser();
    UserDO getUser(String openid);
    int countUserByOpenid(String openid);
}
