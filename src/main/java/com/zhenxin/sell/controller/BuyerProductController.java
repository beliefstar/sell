package com.zhenxin.sell.controller;

import com.zhenxin.sell.VO.ProductInfoVO;
import com.zhenxin.sell.VO.ProductVO;
import com.zhenxin.sell.VO.ResultVO;
import com.zhenxin.sell.annotation.Cache;
import com.zhenxin.sell.constant.RedisConstant;
import com.zhenxin.sell.dataobject.ProductCategory;
import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.service.CategoryService;
import com.zhenxin.sell.service.ProductInfoService;
import com.zhenxin.sell.utils.ResultVOUtil;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    private final CategoryService categoryService;
    private final ProductInfoService productInfoService;
    private final RedisService redisService;

    @Autowired
    public BuyerProductController(CategoryService categoryService, ProductInfoService productInfoService, RedisService redisService) {
        this.categoryService = categoryService;
        this.productInfoService = productInfoService;
        this.redisService = redisService;
    }

    @GetMapping("/list")
    @Cache(RedisConstant.CACHE_PRODUCTVOLIST_NAME)
    public ResultVO list() {
//        Object list = redisService.get(RedisConstant.CACHE_PRODUCTVOLIST_NAME);
//        if (list != null) {
//            return ResultVOUtil.success(list);
//        }
        /**
         * 1，查询所有上架商品
         */
        List<ProductInfo> upAll = productInfoService.findUpAll();
        List<Integer> types = upAll.stream().map(ProductInfo::getCategoryType).distinct().collect(Collectors.toList());

        /**
         * 2，查询所有类目
         */
        List<ProductCategory> categorys = categoryService.findByCategoryTypeIn(types);

        /**
         * 3，数据拼装
         */
        List<ProductVO> productVOList = new ArrayList<>();
        categorys.forEach(item -> {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(item.getCategoryName());
            productVO.setCategoryType(item.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            upAll.forEach(info -> {
                if (info.getCategoryType().equals(item.getCategoryType())) {
                    ProductInfoVO vo = new ProductInfoVO();
                    BeanUtils.copyProperties(info, vo);

                    productInfoVOList.add(vo);
                }
            });
            productVO.setProductList(productInfoVOList);

            productVOList.add(productVO);
        });
//        redisService.set(RedisConstant.CACHE_PRODUCTVOLIST_NAME, productVOList);
        return ResultVOUtil.success(productVOList);
    }
}
