package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.service.ProductKillService;
import com.zhenxin.sell.utils.KEYUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductKillServiceImpl implements ProductKillService {


    private static Map<String, String> product;
    private static ConcurrentHashMap<String, Integer> stock;
    private static Map<String, String> orders;
    private static ReentrantLock lock = new ReentrantLock();

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
        lock.lock();
        try {
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
            lock.unlock();
        }
    }
}
