package com.imooc.controller;

import com.imooc.Service.SellerService;
import com.imooc.config.ProjectUrlConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.util.CookieUtil;
import com.lly835.bestpay.rest.type.Get;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 10:24 2019/8/25
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redistemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              Map<String,Object> map,
                              HttpServletResponse response) {

        //openid查找
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg",ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //设置token至Redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redistemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);

        //设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,expire);
        return new ModelAndView("redirect:"+"/seller/order/list");
    }
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1 从cookie中查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null) {
            //2 清楚redis
            redistemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3 清楚cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT.getMessage());

        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success",map);

    }
}
