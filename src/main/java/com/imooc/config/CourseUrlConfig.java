package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 22:55 2019/9/11
 */
@Data
@Component
@ConfigurationProperties(prefix = "CourseUrl")
public class CourseUrlConfig {
    //图片验证码url
    private String verifyCodeUrl;

    //表单提交Url
    private String commitUrl;

    //登录Url
    private String loginUrl;

    //验证码本地存放地址
    private String verifyImgDestURL;
}
