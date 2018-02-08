package com.gutongxue.wxapp.service;


import com.gutongxue.wxapp.domain.JokeDO;

import java.util.List;

public interface JokeService {
    List<JokeDO> listJoke(int pageNum, int sizeNum);
    int countJoke();
    int countJokeByContent(String content);
    void insertJoke(JokeDO jokeDO);
    void deleteJoke(int id);
    void updateJokeStatus(int id,int status,String now);
}
