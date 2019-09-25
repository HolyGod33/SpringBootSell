package com.imooc.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 23:33 2019/8/2
 */
@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();

        wxPayH5Config.setAppId(accountConfig.getMpAppId());

        wxPayH5Config.setAppSecret(accountConfig.getMpAppSecret());

        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());

        wxPayH5Config.setMchId(accountConfig.getMchId());

        wxPayH5Config.setMchKey(accountConfig.getMchKey());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();

        bestPayService.setWxPayH5Config(wxPayH5Config);

        return bestPayService;
    }
}
