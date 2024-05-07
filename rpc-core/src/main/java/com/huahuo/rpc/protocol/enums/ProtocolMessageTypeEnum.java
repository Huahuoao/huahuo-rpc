package com.huahuo.rpc.protocol.enums;

import lombok.Getter;

/**
 * 协议消息类型枚举
 */
@Getter
public enum ProtocolMessageTypeEnum {
    REQUEST(0),
    RESPONSE(1),
    HEARTBEAT(2),
    Other(3);
    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum e : ProtocolMessageTypeEnum.values()) {
            if (e.getKey() == key) {
                return e;
            }

        }
        return null;
    }
}