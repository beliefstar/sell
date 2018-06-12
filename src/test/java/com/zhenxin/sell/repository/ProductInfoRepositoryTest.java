package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.enums.ProductStatusEnum;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        ProductInfo info = new ProductInfo();
        info.setCategoryType(3);
        info.setProductName("鸡蛋三明治");
        info.setProductDescription("好吃的");
        info.setProductStatus(ProductStatusEnum.UP.getCode());
        info.setProductId(UUID.randomUUID().toString().replaceAll("-", ""));
        info.setProductIcon("http://xxx.img");
        info.setProductPrice(new BigDecimal(12.5));
        info.setProductStock(100);
        repository.save(info);
    }
}