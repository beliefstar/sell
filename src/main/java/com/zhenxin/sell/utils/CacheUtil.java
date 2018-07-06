package com.zhenxin.sell.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CacheUtil {

    public static <T extends Annotation> T getAnnotation(JoinPoint point, Class<T> cacheAnnotation) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method m = signature.getMethod();
        Class<?> aClass = point.getTarget().getClass();
        try {
            Method method1 = aClass.getDeclaredMethod(m.getName(), m.getParameterTypes());
            return method1.getAnnotation(cacheAnnotation);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
