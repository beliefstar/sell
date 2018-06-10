package com.zhenxin.sell.converter;

import com.zhenxin.sell.dataobject.OrderMaster;
import com.zhenxin.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> list) {
        return list.stream().map(OrderMasterToOrderDTOConverter::convert).collect(Collectors.toList());
    }
}
