package com.imooc.util;

import com.imooc.VO.ResultVO;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 21:07 2018/10/21
 */
public class ResultVOUtil {
    public static ResultVO success(Object object) {
        ResultVO resultVO=new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("success");
        resultVO.setCode(0);
        return  resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
