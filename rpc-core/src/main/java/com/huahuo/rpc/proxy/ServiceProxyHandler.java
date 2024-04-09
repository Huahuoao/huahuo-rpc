package com.huahuo.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.huahuo.rpc.RpcApplication;
import com.huahuo.rpc.config.RpcConfig;
import com.huahuo.rpc.constant.RpcConstant;
import com.huahuo.rpc.model.RpcRequest;
import com.huahuo.rpc.model.RpcResponse;
import com.huahuo.rpc.model.ServiceMetaInfo;
import com.huahuo.rpc.registry.RegistryFactory;
import com.huahuo.rpc.registry.remote.Registry;
import com.huahuo.rpc.serializer.Serializer;
import com.huahuo.rpc.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

// 服务代理类，实现动态代理的InvocationHandler接口
@Slf4j
public class ServiceProxyHandler implements InvocationHandler {

    // 调用代理方法，底层使用http通信
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 选择序列化器
        String serializerType = RpcApplication.getRpcConfig().getSerializer();
        log.info("serializerType: {}", serializerType);
        Serializer serializer = SerializerFactory.getInstance(serializerType);

        // 获取服务接口名称
        String serviceName = method.getDeclaringClass().getName();

        // 构建RpcRequest对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 序列化RpcRequest对象
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 获取RpcConfig对象
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();

            // 获取注册中心实例
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());

            // 设置服务元信息
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);

            // 通过服务名获取服务元信息列表
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());

            // 如果服务元信息列表为空，则抛出异常
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("service not found");
            }

            // 随机获得一个服务 模拟负载均衡
            Random random = new Random();
            int randomIndex = random.nextInt(serviceMetaInfoList.size()); // 生成一个在列表大小范围内的随机数
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(randomIndex);

            // 使用HttpRequest通过POST方式发送请求，并获取响应
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress()).body(bodyBytes).execute()) {
                byte[] result = httpResponse.bodyBytes();

                // 反序列化RpcResponse对象
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);

                // 返回RpcResponse中的数据
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            // 打印IO异常的堆栈信息
            e.printStackTrace();
        }

        // 发生异常或失败时返回null
        return null;
    }
}
