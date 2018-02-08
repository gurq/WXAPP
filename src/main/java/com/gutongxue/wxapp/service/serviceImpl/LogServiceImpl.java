package com.gutongxue.wxapp.service.serviceImpl;

import com.gutongxue.wxapp.dao.LogMapper;
import com.gutongxue.wxapp.domain.LogDO;
import com.gutongxue.wxapp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogMapper logMapper;

    @Override
    public boolean insertLog(LogDO logDO) {
        return logMapper.insertCrawlerLog(logDO);
    }
}
