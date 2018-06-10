package com.zhenxin.sell.utils;


import java.util.UUID;

public class KEYUtil {

    public static String gain() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
