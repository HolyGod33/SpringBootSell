package com.imooc.dto;

import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 16:52 2018/10/24
 */
@Data
public class CartDTO {

    //商品Id
    private String productId;

    //数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}