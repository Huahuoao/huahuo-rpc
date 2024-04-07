package com.huahuo.rpc.config;

import com.huahuo.rpc.server.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {
    private String name = "huahuo-rpc";
    private String version = "1.0";
    private String serverHost = "localhost";
    private Integer serverPort = 8080;
    private String serializer = SerializerKeys.JDK;
}
