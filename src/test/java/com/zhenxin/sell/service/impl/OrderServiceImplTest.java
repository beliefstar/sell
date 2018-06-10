package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.dataobject.OrderDetail;
import com.zhenxin.sell.dto.CartDTO;
import com.zhenxin.sell.dto.OrderDTO;
import com.zhenxin.sell.enums.OrderStatusEnum;
import com.zhenxin.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("学院路50号");
        orderDTO.setBuyerName("李林");
        orderDTO.setBuyerOpenid("123123");
        orderDTO.setBuyerPhone("12312123434");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail detail = new OrderDetail();
        detail.setProductId("af9cf61eae5749ceaaaac57d7ef6f4aa");
        detail.setProductQuantity(1);
        orderDetailList.add(detail);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO order = orderService.createOrder(orderDTO);
        log.info("创建订单：" + order);
    }

    @Test
    public void findOne() {
        OrderDTO one = orderService.findOne("6cbe2b1e82624938ae6415ed6041fb12");
        System.out.println(one);
    }

    @Test
    public void findList() {
        Page<OrderDTO> list = orderService.findList("123111", new PageRequest(0, 3));
        System.out.println(list.getContent());
        Assert.assertNotEquals(0, list.getContent().size());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("6cbe2b1e82624938ae6415ed6041fb12");
        OrderDTO dto = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), dto.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("867ae0fc56054cb08e4921309424a835");
        OrderDTO dto = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), dto.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("867ae0fc56054cb08e4921309424a835");
        OrderDTO dto = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), dto.getPayStatus());
    }

    @Test
    public void findAllList() {
        Page<OrderDTO> list = orderService.findList(new PageRequest(0, 3));
        System.out.println(list.getContent());
        Assert.assertNotEquals(0, list.getContent().size());
    }
}