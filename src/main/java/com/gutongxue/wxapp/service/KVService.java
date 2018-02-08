package com.gutongxue.wxapp.service;

import com.gutongxue.wxapp.domain.KV;

public interface KVService {
    KV getV(String k);
    void insertKV(KV kv);
    void updateKV(KV kv);
}
