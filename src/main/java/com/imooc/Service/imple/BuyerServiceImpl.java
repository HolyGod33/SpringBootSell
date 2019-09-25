package com.imooc.Service.imple;

import com.imooc.Service.BuyerService;
import com.imooc.Service.OrderService;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:06 2018/12/27
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderid);

        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {

        OrderDTO orderDTO=orderService.findOne(orderid);
        if(orderDTO==null) {
            log.error("取消订单 查不到该订单 orderId={}",orderid);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(!openid.equals(orderDTO.getBuyerOpenId())) {
            log.error("查询订单 非单主查询 非法操作 openid={} orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        OrderDTO resultOrder=orderService.cancel(orderDTO);
        return resultOrder;
    }

    private OrderDTO checkOrderOwner(String openid,String orderid) {
        OrderDTO orderDTO=orderService.findOne(orderid);
        if(orderDTO==null) {
            return null;
        }
        if(!openid.equals(orderDTO.getBuyerOpenId())) {
            log.error("查询订单 非单主查询 非法操作 openid={} orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
