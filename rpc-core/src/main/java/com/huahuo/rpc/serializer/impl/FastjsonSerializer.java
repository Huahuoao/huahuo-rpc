package com.huahuo.rpc.serializer.impl;

import com.alibaba.fastjson2.JSON;
import com.huahuo.rpc.serializer.Serializer;

import java.io.IOException;

public class FastjsonSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        return JSON.parseObject(bytes, type);
    }
}
