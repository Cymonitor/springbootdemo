package com.example.demo.module.utils;

import java.util.UUID;

/**
 * @antor ChenYong
 * @Date 15:57 2018/10/16
 */
public class UUIDUtils {

    public static String getUUIDRandom() {
        String UUIDRandom = UUID.randomUUID().toString();
        return UUIDRandom.replaceAll("\\-", "");
    }
}
