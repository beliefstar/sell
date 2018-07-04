package com.zhenxin.sell.service;

public interface ProductKillService {
    String getStock(String productId);
    String killProduct(String productId);
}
