package com.imooc.Service.imple;

import com.imooc.Service.OrderService;
import com.imooc.Service.PayService;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.util.JsonUtil;
import com.imooc.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 12:10 2019/8/2
 */

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(orderDTO.getBuyerName());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] request = {}",JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);

        log.info("[微信支付] response = {}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1. 验证签名
        //2. 支付状态
        //3. 验证支付金额
        //4. 验证支付人
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);

        log.info("[微信支付] 异步通知 payresponse = {}",JsonUtil.toJson(payResponse));
        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在

        if (orderDTO == null) {
            log.error("[微信支付] 异步通知 payresponse不存在 orderId = {}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致(0.10  0.1)
        if(MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("[微信支付] 异步通知 订单金额不一致 orderId = {} 微信通知金额 = {} 系统金额 = {}",payResponse.getOrderId(),
                    payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单状
        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request = {}",refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] response = {}",refundRequest);

        return refundResponse;
    }
}
