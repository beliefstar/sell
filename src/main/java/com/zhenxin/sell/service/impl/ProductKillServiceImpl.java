package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.service.ProductKillService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductKillServiceImpl implements ProductKillService {


    private static Map<String, String> product;
    private static Map<String, Integer> stock;

    static {
        product = new HashMap<>();
        stock = new HashMap<>();
        product.put("123", "牙膏");
        product.put("124", "牙刷");
        stock.put("123", 10000);
        stock.put("124", 10000);
    }

    @Override
    public String getStock(String productId) {
        return "商品:" + product.get(productId) + ", 库存：" + stock.get(productId);
    }

    @Override
    public String killProduct(String productId) {
        Integer s = stock.get(productId);
        if (s == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (s > 0) {
            s = s - 1;
            stock.put(productId, s);
            return "秒杀成功, 商品:" + product.get(productId) + ", 库存：" + stock.get(productId);
        }
        return "秒杀失败, 商品:" + product.get(productId) + ", 库存：" + stock.get(productId);
    }
}
