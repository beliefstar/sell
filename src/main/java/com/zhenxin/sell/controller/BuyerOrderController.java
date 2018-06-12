package com.zhenxin.sell.controller;

import com.zhenxin.sell.VO.ResultVO;
import com.zhenxin.sell.converter.OrderFormToOrderDTOConverter;
import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.form.OrderForm;
import com.zhenxin.sell.service.BuyerService;
import com.zhenxin.sell.service.OrderService;
import com.zhenxin.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    private final OrderService orderService;
    private final BuyerService buyerService;

    @Autowired
    public BuyerOrderController(OrderService orderService, BuyerService buyerService) {
        this.orderService = orderService;
        this.buyerService = buyerService;
    }

    //创建订单
    @PostMapping(value = "/create")
    public ResultVO create(@RequestBody OrderForm orderForm) {

//        if (bindingResult.hasErrors()) {
//            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
//            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }

        OrderDTO orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);
        System.out.println(orderDTO);
        orderDTO = orderService.createOrder(orderDTO);

        Map<String, String> res = new HashMap<>();
        res.put("orderId", orderDTO.getOrderId());
        return ResultVOUtil.success(res);
    }


    //订单列表
    @GetMapping("/list")
    public ResultVO list(@PageableDefault Pageable pageable, String openid) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【获取订单列表】参数不正确, openid={}", openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderDTO> list = orderService.findList(openid, pageable);
        return ResultVOUtil.success(list.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO detail(String openid, String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("【获取订单详情】参数不正确, openid={}, orderId={}", openid, orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(String openid, String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】参数不正确, openid={}, orderId={}", openid, orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrderOne(openid, orderId);
        return ResultVOUtil.success();
    }
}
