package com.huahuo.rpc.config;

import com.huahuo.rpc.registry.RegistryKeys;
import lombok.Data;

@Data
public class RegistryConfig {
  private String registry = RegistryKeys.ETCD;
  private String address = "http://81.71.2.123:2379";
  private String username;
  private String password;
  private Long timeout = 10000L;

}
