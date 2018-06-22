package com.zhenxin.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SellerInfoController {


    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void dd() {
        System.out.println("kaishixieru");
        redisTemplate.opsForValue().set("zhenxinaaa", "666");
    }
}
