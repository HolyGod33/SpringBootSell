package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description 商品 包含类目
 * @Date: created in 17:31 2018/10/21
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 7097863777546530545L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
