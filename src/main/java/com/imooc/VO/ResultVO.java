package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author JXUT CXY
 * @Description http请求返回的最外层对象
 * @Date: created in 17:13 2018/10/21
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO  <T> implements Serializable {


    private static final long serialVersionUID = 3068837394742385883L;
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回的具体内容
    private T data ;

}
