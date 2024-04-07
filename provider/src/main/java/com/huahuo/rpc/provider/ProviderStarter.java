package com.huahuo.rpc.provider;

import com.huahuo.rpc.RpcApplication;
import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.registry.LocalRegistry;
import com.huahuo.rpc.server.HttpServer;
import com.huahuo.rpc.server.impl.VertxHttpServer;
import com.huahuo.rpc.service.UserService;
import com.huahuo.rpc.utils.ConfigUtils;

import java.util.HashSet;
import java.util.Set;

public class ProviderStarter {
    public static void main(String[] args) {
        RpcApplication.init();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //启动Web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
