package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(UUID.randomUUID().toString().replaceAll("-", ""));
        orderDetail.setOrderId("c354037b3c614795a4a38dfe5a84f000");
        orderDetail.setProductIcon("http://xxx.img");
        orderDetail.setProductName("扬州炒饭");
        orderDetail.setProductId("1212");
        orderDetail.setProductPrice(new BigDecimal(9));
        orderDetail.setProductQuantity(1);
        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotEquals(null, save);
    }

    @Test
    public void findByOrderId() {
    }
}