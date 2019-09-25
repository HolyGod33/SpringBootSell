package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:01 2019/7/31
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrl;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = projectUrl.getWechatMpAuthorize() + "/sell/wechat/userinfo/";

        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO,URLEncoder.encode(returnUrl));

        log.info("[微信授权] 获取code result={}",redirectUrl);

        return "redirect:" + redirectUrl;

    }

    @GetMapping("/userinfo")
    public String userinfo(@RequestParam("code") String code ,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e) {
            log.error("[微信网页授权] {}",e.getError());

            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" +openId;
    }
    @GetMapping("/qrauthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        String url = projectUrl.getWechatOpenAuthorize() + "/sell/wechat/qruserinfo";
        String redirectUrl =wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return "redirect" + redirectUrl;
    }
    @GetMapping("/qruserinfo")
    public String qruserinfo(@RequestParam("code") String code ,
                             @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e) {
            log.error("[微信网页授权] {}",e.getError());

            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("WxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" +openId;
    }
}
