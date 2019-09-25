package com.imooc.Service;

import com.imooc.dataobject.ProductInfo;

import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:16 2018/10/20
 */
public interface ProductService {
    ProductInfo findOne(String productId);

    //查询商品在架列表
    List<ProductInfo> findUpAll();

    //分页
    Page <ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onsale(String productId);

    //下架
    ProductInfo offsale(String productId);





}
