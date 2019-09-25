package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:10 2019/9/4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j

public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception {
        Map<String, Object> map = new HashMap<>();

        map.put("category_name", "陈祥友");

        map.put("category_type", 1);

        int result = mapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("hahhahaha");
        productCategory.setCategoryType(1);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory result = mapper.findByCategoryType(10);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> result = mapper.findByCategoryName("陈祥友");
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void updateByCategoryType() {
        int result = mapper.updateByCategoryType("吴诗欣",1);
        Assert.assertNotEquals(0,result);

    }

    @Test
    public void updateByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("ha");
        productCategory.setCategoryType(1);
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(3,result);
    }

    @Test
    public void deleteByCategoryType() {
        int result = mapper.deleteByCategoryType(10);

        Assert.assertEquals(1,result);
    }
}