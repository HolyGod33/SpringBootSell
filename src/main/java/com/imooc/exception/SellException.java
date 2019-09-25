package com.imooc.exception;

import com.imooc.enums.ResultEnum;
import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:22 2018/10/23
 */
@Data
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code=resultEnum.getCode();
    }
    public SellException(Integer code,String message) {
        super(message);
        this.code=code;
    }
}
