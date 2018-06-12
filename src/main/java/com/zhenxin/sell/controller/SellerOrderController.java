package com.zhenxin.sell.controller;

import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String list(@PageableDefault(size = 2) Pageable pageable, ModelMap modelMap) {
        Page<OrderDTO> list = orderService.findList(pageable);
        modelMap.put("orderDTOPage", list);
        modelMap.put("currentPage", pageable.getPageNumber());
        return "/order/list";
    }

    @GetMapping("/cancel")
    public String cancel(String orderId, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (Exception e) {
            log.error("【卖家端取消订单】异常：{}", e.getMessage());
            modelMap.put("message", e.getMessage());
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());
        return "order/cancel";
    }

}
