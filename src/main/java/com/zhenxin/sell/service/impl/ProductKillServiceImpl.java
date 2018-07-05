package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.service.ProductKillService;
import com.zhenxin.sell.utils.KEYUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductKillServiceImpl implements ProductKillService {

    private static final Long TIMEOUT = 10 * 1000L;//超时时间

    @Autowired
    private RedisService redisService;

//    @Autowired
//    private RedissonClient redisson;

    private static Map<String, String> product;
    private static ConcurrentHashMap<String, Integer> stock;
    private static Map<String, String> orders;

    static {
        product = new HashMap<>();
        stock = new ConcurrentHashMap<>();
        orders = new HashMap<>();
        product.put("123", "牙膏");
        stock.put("123", 1000);
    }

    @Override
    public String getStock(String productId) {
        return "商品:" + product.get(productId) + ", 库存：" + stock.get(productId) + ", 订单数量：" + orders.size();
    }

    @Override
    public String killProduct(String productId) {
//        RLock rLock = redisson.getLock("anyLock");
        Long timeout = System.currentTimeMillis() + 2000;
        try {
            if (!redisService.lock(productId, timeout)) {
                throw new SellException("【获取锁失败】");
            }
//            rLock.lock(10, TimeUnit.SECONDS);

            Integer s = stock.get(productId);
            if (s == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (s > 0) {
                s = s - 1;
                orders.put(KEYUtil.gain(), productId);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock.put(productId, s);
                return "秒杀成功, 商品:" + product.get(productId) + ", 库存：" + stock.get(productId);
            }
            return "秒杀失败, 商品:" + product.get(productId) + ", 库存：" + stock.get(productId);
        } finally {
            redisService.unlock(productId, timeout);
//            rLock.unlock();
        }
    }
}
