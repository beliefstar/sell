package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.dataobject.SellerInfo;
import com.zhenxin.sell.repository.SellerInfoRepository;
import com.zhenxin.sell.utils.KEYUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void findByOpenid() {
        SellerInfo abccc = sellerService.findByOpenid("abccc");
        Assert.assertNotEquals(null, abccc);
    }

    @Test
    public void saveTest() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid("abccc");
        sellerInfo.setPassword(KEYUtil.gain());
        sellerInfo.setSeller_id(KEYUtil.gain());
        sellerInfo.setUsername("zhenxin");
        sellerInfoRepository.save(sellerInfo);
    }
}