package com.huahuo.rpc.serializer;

import com.huahuo.rpc.serializer.Serializer;
import com.huahuo.rpc.serializer.impl.FastjsonSerializer;
import com.huahuo.rpc.serializer.impl.JdkSerializer;
import com.huahuo.rpc.server.SerializerKeys;
import com.huahuo.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {
  static {
    SpiLoader.load(Serializer.class);
  }
  /**
   * 默认序列化器
   */
  private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

  /**
   * 获取实例
   * @param key
   * @return
   */
  public static Serializer getInstance(String key){
    return SpiLoader.getInstance(Serializer.class,key);
  }
}
