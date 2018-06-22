package com.zhenxin.sell.service;

import com.zhenxin.sell.dataobject.SellerInfo;

public interface SellerService {
    SellerInfo findByOpenid(String openid);
}
