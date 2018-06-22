package com.zhenxin.sell.controller;

import com.zhenxin.sell.dataobject.ProductCategory;
import com.zhenxin.sell.dataobject.ProductInfo;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.service.CategoryService;
import com.zhenxin.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public String findAll(@PageableDefault Pageable pageable, ModelMap modelMap) {
        Page<ProductInfo> infos = productInfoService.findAll(pageable);
        modelMap.put("productInfoPage", infos);
        modelMap.put("currentPage", pageable.getPageNumber());
        return "product/list";
    }

    @GetMapping("/onSale")
    public String onSale(String productId, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/product/list");
        try {
            productInfoService.onSale(productId);
        } catch (Exception e) {
            modelMap.put("message", e.getMessage());
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());

        return "common/info";
    }

    @GetMapping("/offSale")
    public String offSale(String productId, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/product/list");
        try {
            productInfoService.offSale(productId);
        } catch (Exception e) {
            modelMap.put("message", e.getMessage());
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());

        return "common/info";
    }

    @GetMapping("/index")
    public String index(String productId, ModelMap modelMap) {

        //查询所有类目
        List<ProductCategory> categories = categoryService.findAll();
        modelMap.put("categories", categories);

        if (StringUtils.isEmpty(productId)) {
            return "product/index";
        }
        ProductInfo one = productInfoService.findOne(productId);
        if (one == null) {
            modelMap.put("url", "/sell/seller/product/list");
            modelMap.put("message", ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            return "common/error";
        }
        modelMap.put("productInfo", one);
        return "product/index";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(ProductInfo productInfo, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/product/list");
        try {
            productInfoService.save(productInfo);
        } catch (Exception e) {
            modelMap.put("url", "/sell/seller/product/index");
            modelMap.put("message", ResultEnum.OPTION_ERROR.getMessage());
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());
        return "common/info";
    }
}
