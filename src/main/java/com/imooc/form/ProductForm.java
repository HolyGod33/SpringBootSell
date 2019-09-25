package com.imooc.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 9:53 2019/8/20
 */
@Data
public class ProductForm {

    private String  productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //描述
    private String productDescription;

    //库存
    private Integer productStock;

    //小图
    private String productIcon;

    //类目编号
    private Integer categoryType;
}
