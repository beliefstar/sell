package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findSellerInfoByOpenid(String openid);
}
