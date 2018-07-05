package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.constant.RedisConstant;
import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.dto.CartDTO;
import com.zhenxin.sell.enums.ProductStatusEnum;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.repository.ProductInfoRepository;
import com.zhenxin.sell.service.ProductInfoService;
import com.zhenxin.sell.utils.KEYUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository repository;
    private final RedisService redisService;

    @Autowired
    public ProductInfoServiceImpl(ProductInfoRepository repository, RedisService redisService) {
        this.repository = repository;
        this.redisService = redisService;
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

    @Override
    public void incrementStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer t = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(t);
            repository.save(productInfo);
        }
    }

    @Transactional
    @Override
    public void decrementStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo one = repository.findOne(cartDTO.getProductId());
            if (one == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer t = one.getProductStock() - cartDTO.getProductQuantity();
            if (t > 0) {
                one.setProductStock(t);
                repository.save(one);
            } else {
                throw new SellException(ResultEnum.LACK_OF_STOCK);
            }
        }
    }

    @Override
    public ProductInfo onSale(String productId) {

        ProductInfo info = findOne(productId);

        if (info == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (info.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        info.setProductStatus(ProductStatusEnum.UP.getCode());

        return repository.save(info);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo info = findOne(productId);

        if (info == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (info.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        info.setProductStatus(ProductStatusEnum.DOWN.getCode());

        return repository.save(info);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        if (StringUtils.isEmpty(productInfo.getProductId())) {
            productInfo.setProductId(KEYUtil.gain());
        }
        redisService.del(RedisConstant.CACHE_PRODUCTVOLIST_NAME);
        return repository.save(productInfo);
    }
}
