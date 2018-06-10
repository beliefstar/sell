package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.ProductInfo;
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
        info.setCategoryType(1);
        info.setProductName("红烧肉");
        info.setProductDescription("棒棒的");
        info.setProductStatus(0);
        info.setProductId(UUID.randomUUID().toString().replaceAll("-", ""));
        info.setProductIcon("http://xxx.img");
        info.setProductPrice(new BigDecimal(15));
        info.setProductStock(100);
        repository.save(info);
    }
}