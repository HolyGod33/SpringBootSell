package com.imooc.Service.imple;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:50 2018/10/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory=categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList=categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(Arrays.asList(2,3,9));
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory("nansheng",7);
        ProductCategory result = categoryService.save(productCategory);
        //记得写
        Assert.assertNotNull(result);

    }
}