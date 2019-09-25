package com.imooc.Service;

import com.imooc.dto.OrderDTO;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 17:54 2019/9/2
 */
public interface PushMessage {
    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 17:55 2019/9/2
     * @param orderDTO
     * @return:
     * create with IDEA
     *=============================
     */
    void orderStatus(OrderDTO orderDTO);
}
