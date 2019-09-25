package com.imooc.Service.imple;

import com.imooc.Service.PushMessage;
import com.imooc.config.WechatAccountConfig;
import com.imooc.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 17:56 2019/9/2
 */
@Service
@Slf4j
public class PushMessageImpl implements PushMessage {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO)  {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();

        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));

        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenId());

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲爱的"),
                new WxMpTemplateData("keyword1","微信点餐系统"),
                new WxMpTemplateData("keyword2","18879024125"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","$" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎下次光临")
        );

        wxMpTemplateMessage.setData(data);
        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){
            log.error("[微信模板消息] 发送失败,{}",e.getMessage());
        }
    }
}
