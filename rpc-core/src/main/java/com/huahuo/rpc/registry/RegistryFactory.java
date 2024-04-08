package com.huahuo.rpc.registry;

import com.huahuo.rpc.registry.remote.Registry;
import com.huahuo.rpc.registry.remote.impl.EtcdRegistry;
import com.huahuo.rpc.spi.SpiLoader;

public class RegistryFactory {
  private static final Registry DEFAULT_REGISTRY;

  static {
    SpiLoader.load(Registry.class);
    DEFAULT_REGISTRY = new EtcdRegistry();
  }

  public static Registry getInstance(String key) {
    return SpiLoader.getInstance(Registry.class, key);
  }

}
