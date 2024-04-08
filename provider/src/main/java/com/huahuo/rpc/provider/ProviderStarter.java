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

@Slf4j
public class ProviderStarter {
    public static void main(String[] args) {
      RpcApplication.init();
      String serviceName = UserService.class.getName();
      LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
      RpcConfig rpcConfig = RpcApplication.getRpcConfig();
      RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
      Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
      ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
      serviceMetaInfo.setServiceName(serviceName);
      serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
      try {
        registry.register(serviceMetaInfo);
        System.out.println("注册服务成功！");
      } catch (Exception e) {
        e.printStackTrace();
        log.error("register service error", e);
      }
        //启动Web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
