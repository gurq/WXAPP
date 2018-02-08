package com.gutongxue.wxapp.service.serviceImpl;

import com.gutongxue.wxapp.dao.KVMapper;
import com.gutongxue.wxapp.domain.KV;
import com.gutongxue.wxapp.service.KVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KVServiceImpl implements KVService {
    @Autowired
    KVMapper kvMapper;

    @Override
    public KV getV(String k) {
        return kvMapper.getV(k);
    }

    @Override
    public void insertKV(KV kv) {
        kvMapper.insertKV(kv);
    }

    @Override
    public void updateKV(KV kv) {
        kvMapper.updateKV(kv);
    }
}
