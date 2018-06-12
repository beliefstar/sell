package com.zhenxin.sell.repository;

import com.zhenxin.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void getOne() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("饮料");
        productCategory.setCategoryType(1);
        ProductCategory category = repository.save(productCategory);
        System.out.println(category);
    }

    @Test
    public void findOne() {
        ProductCategory one = repository.findOne(1);
        System.out.println(one);
        one.setCategoryName("最爱");
        repository.save(one);
        System.out.println(one);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1, 12, 2);
        List<ProductCategory> typeIn = repository.findByCategoryTypeIn(list);
        System.out.println(typeIn);
        Assert.assertNotEquals(0, typeIn.size());
    }

    @Test
    public void saveTest() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("人气组合");
        category.setCategoryType(3);
        category = repository.save(category);
        System.out.println(category);
    }
}