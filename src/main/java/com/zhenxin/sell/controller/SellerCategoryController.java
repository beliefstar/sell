package com.zhenxin.sell.controller;

import com.zhenxin.sell.dataobject.ProductCategory;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(ModelMap modelMap) {
        List<ProductCategory> categoryList = categoryService.findAll();
        modelMap.put("categoryList", categoryList);
        return "category/list";
    }

    @GetMapping("/index")
    public String index(Integer categoryId, ModelMap modelMap) {
        if (categoryId == null) {
            return "category/index";
        }
        ProductCategory productCategory;
        try {
            productCategory = categoryService.findOne(categoryId);
        } catch (Exception e) {
            modelMap.put("url", "/sell/seller/category/list");
            modelMap.put("message", e.getMessage());
            return "common/error";
        }
        modelMap.put("productCategory", productCategory);
        return "category/index";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(ProductCategory productCategory, ModelMap modelMap) {
        modelMap.put("url", "/sell/seller/category/list");
        try {
            categoryService.save(productCategory);
        } catch (Exception e) {
            modelMap.put("message", e.getMessage());
            return "common/error";
        }
        modelMap.put("message", ResultEnum.OPTION_SUCCESS.getMessage());
        return "common/info";
    }
}
