package com.zhenxin.sell.service;

import com.zhenxin.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    //创建订单
    OrderDTO createOrder(OrderDTO orderDTO);

    //查询 单一订单
    OrderDTO findOne(String orderId);

    //查询 订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

    //查询 所有用户订单列表
    Page<OrderDTO> findList(Pageable pageable);
}
