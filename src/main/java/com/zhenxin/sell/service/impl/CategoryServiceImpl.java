package com.zhenxin.sell.service.impl;

import com.zhenxin.sell.constant.RedisConstant;
import com.zhenxin.sell.dataobject.ProductCategory;
import com.zhenxin.sell.enums.ResultEnum;
import com.zhenxin.sell.exception.SellException;
import com.zhenxin.sell.repository.ProductCategoryRepository;
import com.zhenxin.sell.service.CategoryService;
import com.zhenxin.sell.utils.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ProductCategoryRepository repository;
    private final RedisService redisService;

    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository repository, RedisService redisService) {
        this.repository = repository;
        this.redisService = redisService;
    }

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory category) {

        redisService.del(RedisConstant.CACHE_PRODUCTVOLIST_NAME);

        List<ProductCategory> categoryList = categoryTypeCheck(category.getCategoryType());

        if (category.getCategoryId() == null) {
            if (!categoryList.isEmpty()) {
                throw new SellException(ResultEnum.CATEGORY_IS_EXIST);
            }
            return repository.save(category);
        }

        if (categoryList.size() == 0) {
            return repository.save(category);
        }

        if (categoryList.size() == 1 && categoryList.get(0).getCategoryId().equals(category.getCategoryId())) {
            return repository.save(category);
        }
        throw new SellException(ResultEnum.CATEGORY_IS_EXIST);
    }

    private List<ProductCategory> categoryTypeCheck(Integer categoryType) {
        List<Integer> list = new ArrayList<>();
        list.add(categoryType);

        return findByCategoryTypeIn(list);
    }
}
