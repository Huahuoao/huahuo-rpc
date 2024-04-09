package com.huahuo.rpc.registry.remote.impl;

import cn.hutool.json.JSONUtil;
import com.huahuo.rpc.config.RegistryConfig;
import com.huahuo.rpc.model.ServiceMetaInfo;
import com.huahuo.rpc.registry.remote.Registry;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

// EtcdRegistry类实现了Registry接口，用于Etcd注册中心的注册、注销和服务发现
@Slf4j
public class EtcdRegistry implements Registry {
    private Client client;  // Etcd客户端
    private KV kvClient;  // KV客户端，用于键值对操作
    private static final String ETCD_ROOT_PATH = "/rpc/";  // Etcd根路径

    @Override
    // 初始化EtcdRegistry，根据RegistryConfig创建Etcd客户端和KV客户端
    public void init(RegistryConfig registryConfig) {
        client = Client.builder().endpoints(registryConfig.getAddress()).connectTimeout(Duration.ofMillis(registryConfig.getTimeout())).build();
        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        try {
            Lease leaseClient = client.getLeaseClient();
//      long leaseId = leaseClient.grant(30).get().getID();  // 设置租约时间为30秒
            long leaseId = leaseClient.grant(99999).get().getID(); //先不实现自动续期功能，用99999s代替
            String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();  // 注册键
            ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
            PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
            kvClient.put(key, value, putOption).whenComplete((putResponse, throwable) -> {
                if (throwable != null) {
                    // 异常处理
                    log.error("Error occurred while putting service info into Etcd", throwable);
                } else {
                    log.info("Service registered successfully: " + serviceMetaInfo);
                }
            });
        } catch (Exception e) {
            // 具体异常处理
            log.error("Error occurred while registering service", e);
            throw e;
        }
    }


    @Override
    // 从Etcd中注销服务信息
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));  // 删除服务信息
    }

    @Override
    // 根据serviceKey在Etcd中进行服务发现，返回服务列表
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        String searchPrefix = ETCD_ROOT_PATH + serviceKey + "/";  // 搜索前缀
        try {
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(ByteSequence.from(searchPrefix, StandardCharsets.UTF_8), getOption).get().getKvs();  // 获取键值对列表
            return keyValues.stream().map(keyValue -> {
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value, ServiceMetaInfo.class);
            }).collect(Collectors.toList());  // 将键值对转换为ServiceMetaInfo对象列表
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);  // 抛出异常
        }
    }

    @Override
    // 销毁EtcdRegistry，关闭客户端连接
    public void destroy() {
        System.out.println("当前节点下线");  // 输出信息
        if (kvClient != null) {
            kvClient.close();  // 关闭KV客户端
        }
        if (client != null) {
            client.close();  // 关闭Etcd客户端
        }
    }
}
