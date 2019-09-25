package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author JXUT CXY
 * @Description Jpa资源库，提供自动建表
 * @Date: created in 18:53 2018/10/17
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    //通过类目Id找数据库对象
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
