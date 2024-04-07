package com.huahuo.rpc.consumer;

import com.huahuo.rpc.model.User;
import com.huahuo.rpc.proxy.ServiceProxyFactory;
import com.huahuo.rpc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

public class ConsumerTest {
  @Test
  public void testConsumer(){
   long begin= System.nanoTime();
    for (int i = 0; i < 100; i++) {
      UserService userService = ServiceProxyFactory.getProxy(UserService.class);
      userService.getUser(new User("花火"+i));
    }
    long end = System.nanoTime();
    System.out.println("============执行完毕============");
    System.out.println("============用时："+(end-begin)/1000000+"ms============");
  }
}
