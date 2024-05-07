package com.huahuo.rpc.protocol.enums;

import lombok.Getter;

@Getter
public enum ProtocolMessageStatusEnum {
    OK("ok", 200),
    BAD_REQUEST("badRequest", 400),
    BAD_RESPONSE("badResponse", 500);
    private final String message;
    private final int code;

    ProtocolMessageStatusEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public static ProtocolMessageStatusEnum getByCode(int code) {
        for (ProtocolMessageStatusEnum value : ProtocolMessageStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

}
