package com.huahuo.rpc.consumer;


import com.huahuo.rpc.model.User;
import com.huahuo.rpc.proxy.ServiceProxyFactory;
import com.huahuo.rpc.service.UserService;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Map;

public class ConsumerTest {
  @Test
  public void testConsumer(){
    UserService userService = ServiceProxyFactory.getProxy(UserService.class);
    Map<String, String> map = userService.getUser(new User("花火"));
    System.out.println(map);
  }
}
