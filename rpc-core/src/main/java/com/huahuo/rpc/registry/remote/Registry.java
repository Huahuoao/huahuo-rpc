package com.huahuo.rpc.registry.remote;

import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.model.ServiceMetaInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 远程注册中心接口
public interface Registry {
  // 初始化远程注册中心配置
  void init(RegistryConfig registryConfig);
  // 注册服务元信息
  void register(ServiceMetaInfo serviceMetaInfo) throws Exception;
  // 取消注册服务元信息
  void unRegister(ServiceMetaInfo serviceMetaInfo);
  // 服务发现，根据服务关键字查询服务元信息列表
  List<ServiceMetaInfo> serviceDiscovery(String serviceKey);
  // 销毁远程注册中心
  void destroy();
  // 心跳检测
  void heartBeat();
}
