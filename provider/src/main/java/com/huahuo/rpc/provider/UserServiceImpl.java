package com.huahuo.rpc.provider;

import cn.hutool.core.date.DateUtil;
import com.huahuo.rpc.RpcApplication;
import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.model.User;
import com.huahuo.rpc.service.UserService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    @Override
    public Map<String, String> getUser(User user) {
        Map<String, String> map = new HashMap<>();
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        user.setName(user.getName() + "啦啦啦");
        map.put("处理后用户名", user.getName());
        map.put("调用时间", DateUtil.now());
        map.put("处理方法", UserServiceImpl.class.getName());
        map.put("注册中心地址", registryConfig.getAddress());
        map.put("服务提供方地址", rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
        return map;
    }
}
