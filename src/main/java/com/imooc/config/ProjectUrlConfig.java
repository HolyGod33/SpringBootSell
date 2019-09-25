package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:01 2019/8/24
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    //公众平台Url
    public String wechatMpAuthorize;

    //微信开放平台授权Url
    public String wechatOpenAuthorize;

    public String sell;
}
