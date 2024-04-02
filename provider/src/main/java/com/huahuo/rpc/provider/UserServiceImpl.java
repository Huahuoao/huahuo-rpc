package com.huahuo.rpc.provider;

import com.huahuo.rpc.model.User;
import com.huahuo.rpc.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return "用户名：" + user.getName();
    }
}
