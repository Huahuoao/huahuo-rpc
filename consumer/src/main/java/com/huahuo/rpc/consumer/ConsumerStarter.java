package com.huahuo.rpc.consumer;

import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.utils.ConfigUtils;

public class ConsumerStarter {
    public static void main(String[] args) {
      RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
    }
}
