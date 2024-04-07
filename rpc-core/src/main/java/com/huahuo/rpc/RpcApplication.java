package com.huahuo.rpc;


import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.constant.RpcConstant;
import com.huahuo.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

import java.rmi.registry.Registry;

/**
 * RPC 框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 *
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}", newRpcConfig.toString());
    }

    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);

        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        System.out.println("config=>"+newRpcConfig);
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
