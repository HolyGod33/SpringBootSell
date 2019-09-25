package com.imooc.Handler;

import com.imooc.VO.ResultVO;
import com.imooc.config.ProjectUrlConfig;
import com.imooc.exception.ResponseBankException;
import com.imooc.exception.SellAuthorizeException;
import com.imooc.exception.SellException;
import com.imooc.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:06 2019/8/27
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"))
                ;
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    //使这个异常返回的是一个指定的json格式而不是500类型的错误
    public ResultVO handleSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus
    public void handleResponseBankException() {

    }
}
