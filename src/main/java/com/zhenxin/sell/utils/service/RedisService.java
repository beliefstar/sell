package com.zhenxin.sell.utils.service;

import java.util.Collection;

public interface RedisService {

    void set(String key, Object value);

    void set(String key, Object value, Long timeout);

    Object get(String key);

    Boolean has(String key);

    void del(String key);

    void del(Collection<String> key);

    Boolean expire(String key, Long miniTime);
}
