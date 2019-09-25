package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author JXUT CXY
 * @Description 商品详情
 * @Date: created in 17:41 2018/10/21
 */
@Data
public class ProductInfoVO implements Serializable {


    private static final long serialVersionUID = -3895834204864685262L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
