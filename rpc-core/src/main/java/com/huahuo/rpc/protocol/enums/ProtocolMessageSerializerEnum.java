package com.huahuo.rpc.protocol.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum ProtocolMessageSerializerEnum {
    JDK(0, "jdk"),
    FASTJSON(1, "fastjson");

    private final int key;
    private final String value;

    ProtocolMessageSerializerEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public static ProtocolMessageSerializerEnum getEnum(int key) {
        for (ProtocolMessageSerializerEnum item : values()) {
            if (item.getKey() == key) {
                return item;
            }
        }
        return null;
    }
}
