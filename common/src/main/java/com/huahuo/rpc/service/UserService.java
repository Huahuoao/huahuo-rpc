package com.huahuo.rpc.service;

import com.huahuo.rpc.model.User;

import java.net.UnknownHostException;
import java.util.Map;

public interface UserService {
   Map<String,String> getUser(User user) throws UnknownHostException;
}
