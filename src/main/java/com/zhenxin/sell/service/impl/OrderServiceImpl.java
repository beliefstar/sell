package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.converter.OrderMasterToOrderDTOConverter;
import com.zhenxin.sell.dataobject.OrderDetail;
import com.zhenxin.sell.dataobject.OrderMaster;
import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.dto.CartDTO;
import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.OrderStatusEnum;
import com.zhenxin.sell.enums.PayStatusEnum;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.repository.OrderDetailRepository;
import com.zhenxin.sell.repository.OrderMasterRepository;
import com.zhenxin.sell.service.OrderService;
import com.zhenxin.sell.service.ProductInfoService;
import com.zhenxin.sell.utils.KEYUtil;
import javafx.print.Collation;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMasterRepository orderMasterRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductInfoService productInfoService;

    @Autowired
    public OrderServiceImpl(OrderMasterRepository orderMasterRepository, OrderDetailRepository orderDetailRepository, ProductInfoService productInfoService) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productInfoService = productInfoService;
    }


    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        String orderId = KEYUtil.gain();
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        for (OrderDetail detail : orderDTO.getOrderDetailList()) {
            //1， 查询商品（数量，价格）
            ProductInfo productInfo = productInfoService.findOne(detail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2， 计算总价
            totalPrice = productInfo.getProductPrice()
                    .multiply(new BigDecimal(detail.getProductQuantity()))
                    .add(totalPrice);

            BeanUtils.copyProperties(productInfo, detail);
            detail.setOrderId(orderId);
            detail.setDetailId(KEYUtil.gain());

            //3， 写入数据库（订单详细表）
            orderDetailRepository.save(detail);
        }

        //3， 写入数据库（订单总表）
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4， 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decrementStock(cartDTOList);

        return OrderMasterToOrderDTOConverter.convert(orderMaster);
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> dtoList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(dtoList, pageable, orderMasterPage.getTotalElements());
    }

    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //验证状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //返回库存
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();

        List<CartDTO> cartDTOList = orderDetailList.stream().map(item ->
                new CartDTO(item.getProductId(), item.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.incrementStock(cartDTOList);

        //如果已经支付，需退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // todo 退款
        }

        //更改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster save = orderMasterRepository.save(orderMaster);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> all = orderMasterRepository.findAll(pageable);
        List<OrderDTO> dtoList = OrderMasterToOrderDTOConverter.convert(all.getContent());

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }
}
