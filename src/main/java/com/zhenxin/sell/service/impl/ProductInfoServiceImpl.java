package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.enums.ProductStatusEnum;
import com.zhenxin.sell.repository.ProductInfoRepository;
import com.zhenxin.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository repository;

    @Autowired
    public ProductInfoServiceImpl(ProductInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
