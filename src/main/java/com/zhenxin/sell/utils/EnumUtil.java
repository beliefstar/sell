package com.zhenxin.sell.utils;

import com.zhenxin.sell.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getMessageByCode(Integer code, Class<T> enumClass) {
        for (T item : enumClass.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
