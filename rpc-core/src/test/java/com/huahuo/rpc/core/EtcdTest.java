package com.huahuo.rpc.core;

import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.model.ServiceMetaInfo;
import com.huahuo.rpc.registry.remote.Registry;
import com.huahuo.rpc.registry.remote.impl.EtcdRegistry;
import org.junit.Before;
import org.junit.Test;


public class EtcdTest {
  // 在测试方法中，演示如何使用 jetcd 客户端进行基本的 etcd 操作
  final Registry registry = new EtcdRegistry();

  @Before
  public void init() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("http://81.71.2.123:2379");
    registry.init(registryConfig);
  }

  @Test
  public void register() throws Exception {
    ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
    serviceMetaInfo.setServiceName("com.huahuo.rpc.test.HelloService");
    serviceMetaInfo.setServiceAddress("127.0.0.1:8080");
    serviceMetaInfo.setServiceVersion("1.0.0");
    registry.register(serviceMetaInfo);
    ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
    serviceMetaInfo1.setServiceName("com.huahuo.rpc.test.HelloService");
    serviceMetaInfo1.setServiceAddress("127.0.0.1:8081");
    serviceMetaInfo1.setServiceVersion("1.0.1");
    registry.register(serviceMetaInfo1);

  }

  }






