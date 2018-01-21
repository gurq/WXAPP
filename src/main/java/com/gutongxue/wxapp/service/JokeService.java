package com.gutongxue.wxapp.service;


import com.gutongxue.wxapp.domain.JokeDO;

import java.util.List;

public interface JokeService {
    List<JokeDO> listJoke(int pageNum, int sizeNum);
}
