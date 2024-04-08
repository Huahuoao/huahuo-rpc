package com.huahuo.rpc.registry.remote;

import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.model.ServiceMetaInfo;

import java.util.List;

public interface Registry {
  void init(RegistryConfig registryConfig);
  void register(ServiceMetaInfo serviceMetaInfo) throws Exception;
  void unRegister(ServiceMetaInfo serviceMetaInfo);
  List<ServiceMetaInfo> serviceDiscovery(String serviceKey);
  void destroy();
}
