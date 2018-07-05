package com.zhenxin.sell.utils.service.impl;

import com.zhenxin.sell.utils.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void del(Collection<String> key) {
        redisTemplate.delete(key);
    }

    @Override
    public Boolean expire(String key, Long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Boolean lock(String key, Long value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        Long currentValue = (Long) get(key);
        if (currentValue != null && currentValue < System.currentTimeMillis()) {
            //超时
            Object nValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (nValue != null && currentValue.equals(nValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unlock(String key, Long value) {
        try {
            Long currentValue = (Long) get(key);

            if (currentValue != null && currentValue.equals(value)) {
                del(key);
            }
        } catch (Exception e) {
            log.error("【解锁失败】msg:{}", e);
        }
    }
}
