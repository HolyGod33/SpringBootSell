package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:55 2018/10/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        if (productCategory != null) {
            System.out.println(productCategory.toString());
        }else {
            System.out.println("============");
        }
    }
    //更新/添加数据
    @Test
    //测试用，成功后即回到之前测试的状态(称之为回滚)
    @Transactional
    public void saveTest(){
        //创建对象，将需要修改的条目找到后赋给pro
        ProductCategory productCategory = new ProductCategory("girl's love",10);
        //修改
        //productCategory.setCategoryName("holyshit");
        //productCategory.setCategoryType(9);

        //用一个对象来存储save后的结果
        ProductCategory result=repository.save(productCategory);
        //判断是否保存成功
        Assert.assertNotNull(result);
    }
    @Test
    public void  findByCategoryTypeInTest() {
        //将数组转化为List
        List<Integer> list = Arrays.asList(2,3,9,10);
        List<ProductCategory> result=repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}