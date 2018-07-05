package com.zhenxin.sell.aspect;

import com.zhenxin.sell.annotation.Cache;
import com.zhenxin.sell.utils.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private RedisService redisService;

    @Around("@annotation(com.zhenxin.sell.annotation.Cache)")
    public Object cacheProxy(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method m = signature.getMethod();
        Class<?> aClass = point.getTarget().getClass();
        String cacheName = "";
        try {
            Method method1 = aClass.getDeclaredMethod(m.getName(), m.getParameterTypes());
            Cache annotation = method1.getAnnotation(Cache.class);
            cacheName = annotation.value();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

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
