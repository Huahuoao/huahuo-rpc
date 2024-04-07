package com.huahuo.rpc.consumer;

import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.model.User;
import com.huahuo.rpc.proxy.ServiceProxyFactory;
import com.huahuo.rpc.service.UserService;
import com.huahuo.rpc.utils.ConfigUtils;

public class ConsumerStarter {
    public static void main(String[] args) {
       UserService userService = ServiceProxyFactory.getProxy(UserService.class);
       System.out.println(userService.getUser(new User("花火")));
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpcConfig);
    }
}
