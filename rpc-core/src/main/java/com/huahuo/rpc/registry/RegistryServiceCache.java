package com.huahuo.rpc.registry;

import com.huahuo.rpc.model.ServiceMetaInfo;

import java.util.List;

public class RegistryServiceCache {
    List<ServiceMetaInfo> serviceCache;

    public void writeCache(List<ServiceMetaInfo> serviceCache) {
        this.serviceCache = serviceCache;
    }

    public List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    public void clearCache() {
        this.serviceCache = null;
    }
}
