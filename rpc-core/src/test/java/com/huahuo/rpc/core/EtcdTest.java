package com.huahuo.rpc.core;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EtcdTest {
  // 在测试方法中，演示如何使用 jetcd 客户端进行基本的 etcd 操作
  @Test
  public void etcdDemo() throws ExecutionException, InterruptedException {
    // 创建一个 etcd 客户端实例，并指定 etcd 的地址
    Client client = Client.builder().endpoints("http://10.0.8.12:2379").build();
    // 从客户端获取 KV 客户端，用于进行键值对操作
    KV kvClient = client.getKVClient();
    // 定义要操作的键
    ByteSequence key = ByteSequence.from("test.key".getBytes());
    // 定义要存储的值
    ByteSequence value = ByteSequence.from("test_value".getBytes());
    // 向 etcd 中写入键值对，并通过 get() 方法等待操作完成
    kvClient.put(key, value).get();
    // 使用 CompletableFuture 异步获取指定键的值
    CompletableFuture<GetResponse> getFuture = kvClient.get(key);
    // 获取异步操作的结果，通过 get() 方法等待操作完成
    GetResponse response = getFuture.get();
    // 输出获取到的响应信息
    System.out.println(response.toString());
    // 删除之前存储的键值对，并通过 get() 方法等待操作完成
    kvClient.delete(key).get();
  }
}
