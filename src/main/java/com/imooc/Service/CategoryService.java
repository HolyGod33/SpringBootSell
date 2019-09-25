package com.imooc.Service;

import com.imooc.dataobject.ProductCategory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:09 2018/10/18
 */
@Service
@Transactional
public interface CategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
