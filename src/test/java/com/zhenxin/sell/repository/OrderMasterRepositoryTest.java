package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() {
        OrderMaster order = new OrderMaster();
        order.setBuyerAddress("江北福佑路40号");
        order.setBuyerName("甄鑫");
        order.setBuyerOpenid("123456");
        order.setBuyerPhone("13112129090");
        order.setOrderAmount(new BigDecimal(22));
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
        repository.save(order);
    }

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> page = repository.findByBuyerOpenid("123456", new PageRequest(0, 5));
        System.out.println(page);

    }
}