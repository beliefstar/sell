package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.dataobject.SellerInfo;
import com.zhenxin.sell.repository.SellerInfoRepository;
import com.zhenxin.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        if (!StringUtils.isEmpty(openid)) {
            return sellerInfoRepository.findSellerInfoByOpenid(openid);
        }
        return null;
    }
}
