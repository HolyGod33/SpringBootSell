package com.imooc.Service;
import com.imooc.dto.OrderDTO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 17:54 2018/10/23
 */
public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询单个订单
    OrderDTO findOne(String orderId);

    //查询订单列表
    Page<OrderDTO> findList(String buyOpenId, Pageable pageable);


    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

    Page<OrderDTO> findList(Pageable pageable);
}
