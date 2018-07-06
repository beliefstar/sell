package com.zhenxin.sell.aspect;

import com.zhenxin.sell.annotation.CacheFlush;
import com.zhenxin.sell.utils.CacheUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheFlushAspect {

    @Autowired
    private RedisService redisService;

    @Before("@annotation(com.zhenxin.sell.annotation.CacheFlush)")
    public void before(JoinPoint joinpoint) {
        CacheFlush flush = CacheUtil.getAnnotation(joinpoint, CacheFlush.class);
        assert flush != null;

        String cacheName = flush.value();

        redisService.del(cacheName);
    }

}
