package com.huahuo.rpc.provider;

import com.huahuo.rpc.RpcApplication;
import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.model.ServiceMetaInfo;
import com.huahuo.rpc.registry.RegistryFactory;
import com.huahuo.rpc.registry.local.LocalRegistry;
import com.huahuo.rpc.registry.remote.Registry;
import com.huahuo.rpc.server.HttpServer;
import com.huahuo.rpc.server.impl.VertxHttpServer;
import com.huahuo.rpc.service.UserService;
import lombok.extern.slf4j.Slf4j;

// 服务提供者启动类
@Slf4j
public class ProviderStarter {
  public static void main(String[] args) {
    try {
      // 初始化RPC应用
      RpcApplication.init();
      String serviceName = UserService.class.getName();
      // 在本地注册中心中注册服务
      LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
      RpcConfig rpcConfig = RpcApplication.getRpcConfig();
      RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
      Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
      ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
      serviceMetaInfo.setServiceName(serviceName);
      serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
      try {
        // 向注册中心注册服务信息
        registry.register(serviceMetaInfo);
        System.out.println("成功注册服务： " + serviceName + " 到注册中心");
        System.out.println("服务地址：" + serviceMetaInfo.getServiceAddress());
      } catch (Exception e) {
        // 注册服务出错时记录日志
        e.printStackTrace();
        log.error("register service error", e);
      }
      // 启动Web服务
      HttpServer httpServer = new VertxHttpServer();
      httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    } catch (Exception e) {
      // 服务提供者启动出错时记录日志
      e.printStackTrace();
      log.error("provider start error", e);
    }
  }
}
