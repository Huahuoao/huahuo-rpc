package com.huahuo.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {
    /**
     * 获取服务接口的代理对象
     *
     * @param serviceClass 服务接口的Class对象
     * @param <T>          泛型类型
     * @return             代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxyHandler()
        );
    }
}
