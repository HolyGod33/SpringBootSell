package com.imooc.Service;

import com.imooc.dto.OrderDTO;
import org.hibernate.criterion.Order;

/**
 * @Author JXUT CXY
 * @Description 买家
 * @Date: created in 18:02 2018/12/27
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderid);

    //取消一个订单
    OrderDTO cancelOrder(String openid,String orderid);
}
