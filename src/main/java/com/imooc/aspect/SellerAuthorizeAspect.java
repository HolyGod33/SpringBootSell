package com.imooc.aspect;

import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.exception.SellAuthorizeException;
import com.imooc.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 10:24 2019/8/27
 */
@Aspect
@Slf4j
@Component
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ServerEndpointExporter serverEndpointExporter;

    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))" +
    "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify() {
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[登录校验] Cookie中查不到token");
            throw new SellAuthorizeException();
        }

        //redis查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("[登录校验] Redis查不到token");
            throw new SellAuthorizeException();
        }
    }
}
