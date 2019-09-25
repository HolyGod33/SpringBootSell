package com.imooc.enums;

import lombok.Getter;

/**
 * @Author JXUT CXY
 * @Description 商品状态
 * @Date: created in 18:35 2018/10/20
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{

    UP(0,"在架"),
    DOWN(1,"下架");
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message=message;
    }
}
