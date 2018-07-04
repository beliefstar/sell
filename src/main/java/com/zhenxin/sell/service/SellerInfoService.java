package com.zhenxin.sell.service;

import com.zhenxin.sell.dataobject.SellerInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SellerInfoService {
    void register(SellerInfo info);

    void login(SellerInfo info, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
