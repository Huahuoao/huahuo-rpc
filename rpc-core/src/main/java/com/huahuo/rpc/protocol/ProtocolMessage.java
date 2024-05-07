package com.huahuo.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 协议消息类，泛型 T 代表消息体的类型
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {
    private Header header;  // 消息头部
    private T body;  // 消息体

    @Data
    // 消息头部内部静态类
    public static class Header {
        private byte magic;  // 魔数
        private byte version;  // 协议版本
        private byte serializer;  // 序列化器类型
        private byte status;  // 消息状态
        private long requestId;  // 请求ID
        private int bodyLength;  // 消息体长度
    }
}
