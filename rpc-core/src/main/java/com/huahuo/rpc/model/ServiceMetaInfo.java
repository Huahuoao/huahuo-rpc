package com.huahuo.rpc.model;

import lombok.Data;

// 服务元信息类
@Data
public class ServiceMetaInfo {
  private String serviceName; // 服务名
  private String serviceVersion = "1.0"; // 服务版本，默认为"1.0"
  private String serviceAddress; // 服务地址
  private String serviceGroup = "default"; // 服务分组，默认为"default"

  // 获取服务键
  public String getServiceKey() {
    return String.format("%s:%s", serviceName, serviceVersion);
  }

  // 获取服务节点键
  public String getServiceNodeKey() {
    return String.format("%s/%s", getServiceKey(), serviceAddress);
  }


}
