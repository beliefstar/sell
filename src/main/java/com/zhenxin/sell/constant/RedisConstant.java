package com.zhenxin.sell.constant;

public interface RedisConstant {
    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 7200; //2小时

    String CACHE_PRODUCTVOLIST_NAME = "cache_product_vo_list";
}
