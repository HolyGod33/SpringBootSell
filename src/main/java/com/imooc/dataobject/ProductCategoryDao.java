package com.imooc.dataobject;

import com.imooc.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 16:04 2019/9/6
 */
public class ProductCategoryDao {
    @Autowired
    private ProductCategoryMapper mapper;

    public List<ProductCategory> findByCategoryName(String  categoryName) {
        return mapper.findByCategoryName(categoryName);
    }
}
