package com.zhenxin.sell.controller;

import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     * @param pageable
     * @param modelMap
     * @return
     */
    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, ModelMap modelMap) {
        Page<OrderDTO> list = orderService.findList(pageable);
        modelMap.put("orderDTOPage", list);
        modelMap.put("currentPage", pageable.getPageNumber());
        return "/order/list";
    }

    /**
     * 取消订单
     * @param orderId
     * @param modelMap
     * @return
     */
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
        return "order/info";
    }

    /**
     * 订单详情
     * @param orderId
     * @param modelMap
     * @return
     */
    @GetMapping("/detail")
    public String detail(String orderId, ModelMap modelMap) {

        if (StringUtils.isEmpty(orderId)) {
            modelMap.put("message", "【查询订单详细】订单ID不可为空");
            modelMap.put("url", "/sell/seller/order/list");
            return "common/error";
        }
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (Exception e) {
            log.error("【查询订单详细】{}", e.getMessage());
            modelMap.put("message", "【查询订单详细】" + e.getMessage());
            modelMap.put("url", "/sell/seller/order/list");
            return "common/error";
        }

        modelMap.put("orderDTO", orderDTO);
        return "order/detail";
    }

    /**
     * 完结订单
     * @param orderId
     * @param modelMap
     * @return
     */
    @GetMapping("/finish")
    public String finish(String orderId, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/order/list");

        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (Exception e) {
            log.error("【完结订单】{}", e.getMessage());
            modelMap.put("message", "【完结订单】失败！" + e.getMessage());
            modelMap.put("url", "/sell/seller/order/list");
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());
        return "order/info";
    }

}
