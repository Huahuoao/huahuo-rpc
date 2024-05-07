package com.huahuo.rpc.protocol;

// 定义协议常量接口
public interface ProtocolConstant {
    // 消息头长度常量
    int MESSAGE_HEADER_LENGTH = 17;
    // 协议魔数常量
    byte PROTOCOL_MAGIC = 0x1;
    // 协议版本常量
    byte PROTOCOL_VERSION = 0x1;
}
