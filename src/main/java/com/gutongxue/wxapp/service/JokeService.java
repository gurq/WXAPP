package com.gutongxue.wxapp.service;


import com.gutongxue.wxapp.domain.JokeDO;
import com.gutongxue.wxapp.domain.QueryParam;
import com.gutongxue.wxapp.domain.JokeVO;

import java.util.List;

public interface JokeService {
    List<JokeVO> listJoke(QueryParam queryParam);
    int countJoke(QueryParam queryParam);
    int countJokeByContent(String content);
    void insertJoke(JokeDO jokeDO);
    void deleteJoke(int id);
    void updateJokeStatus(int id,int status,String now);
}
