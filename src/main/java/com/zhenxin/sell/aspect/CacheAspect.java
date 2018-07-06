package com.zhenxin.sell.aspect;

import com.zhenxin.sell.annotation.Cache;
import com.zhenxin.sell.utils.CacheUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private RedisService redisService;

    @Around("@annotation(com.zhenxin.sell.annotation.Cache)")
    public Object cacheProxy(ProceedingJoinPoint point) throws Throwable {
        Cache cache = CacheUtil.getAnnotation(point, Cache.class);

        assert cache != null;

        String cacheName = cache.value();

        Object o = redisService.get(cacheName);
        if (o != null) {
            return o;
        }

        try {
            Object proceed = point.proceed();
            if (proceed != null) {
                redisService.set(cacheName, proceed);
            }
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
