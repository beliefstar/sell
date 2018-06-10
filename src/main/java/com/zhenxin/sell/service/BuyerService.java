package com.zhenxin.sell.service;

import com.zhenxin.sell.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrderOne(String openid, String orderId);

}
