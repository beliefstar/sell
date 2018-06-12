package com.zhenxin.sell.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhenxin.sell.dataobject.OrderDetail;
import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.form.OrderForm;

import java.io.IOException;
import java.util.Arrays;

public class OrderFormToOrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

//        ObjectMapper objectMapper  = new ObjectMapper();
//        OrderDetail[] orderDetails;
//        try {
//            orderDetails = objectMapper.readValue(orderForm.getItems(), OrderDetail[].class);
//            if (orderDetails == null || orderDetails.length == 0) {
//                throw new SellException(ResultEnum.CART_EMPTY);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new SellException(ResultEnum.PARAM_ERROR);
//        }

//        orderDTO.setOrderDetailList(Arrays.asList(orderDetails));
        orderDTO.setOrderDetailList(orderForm.getItems());
        return orderDTO;
    }
}
