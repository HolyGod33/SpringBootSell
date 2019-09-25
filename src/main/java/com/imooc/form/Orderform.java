package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:31 2018/12/24
 */
@Data
public class Orderform {

    //买家姓名
    @NotEmpty(message="x姓名必填")
    private String name;

    //买家手机号
    @NotEmpty(message = "手机号必填")
    private  String phone;

    //买家地址
    @NotEmpty(message = "地址必填")
    private String address;

    //买家OpenId
    @NotEmpty(message = "openid必填")
    private String openid;

    //买家地址
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
