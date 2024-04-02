package com.huahuo.rpc.provider;

import com.huahuo.rpc.registry.LocalRegistry;
import com.huahuo.rpc.server.HttpServer;
import com.huahuo.rpc.server.impl.VertxHttpServer;
import com.huahuo.rpc.service.UserService;

public class ProviderStarter {
    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        //启动Web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
