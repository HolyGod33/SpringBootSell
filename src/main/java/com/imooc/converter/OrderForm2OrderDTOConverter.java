package com.imooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.Orderform;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:45 2018/12/24
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(Orderform orderform) {
        Gson gson=new Gson();

        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setBuyerName(orderform.getName());
        orderDTO.setBuyerPhone(orderform.getPhone());
        orderDTO.setBuyerAddress(orderform.getAddress());
        orderDTO.setBuyerOpenId(orderform.getOpenid());
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderform.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception e){
            log.error("对象转换 错误 String={}",orderform);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
