package com.imooc.Service.imple;

import com.imooc.Service.OrderService;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:37 2018/12/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImpTest {

    @Autowired
    private OrderServiceImp orderService;

    private final String buyerOpenId="1234578986543";

    @Test
    public void create() {

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerAddress("mukewang");
        orderDTO.setBuyerName("naigougou");
        orderDTO.setBuyerOpenId(buyerOpenId);
        orderDTO.setBuyerPhone("12345678987");

        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();

        OrderDetail orderDetai_l=new OrderDetail();
        orderDetai_l.setProductId("969");
        orderDetai_l.setProductQuantity(1);
        orderDetailList.add(orderDetai_l);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result=orderService.create(orderDTO);
        log.info("[创建订单] result = {}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO o1=orderService.findOne("1545214966110191960");
        log.info("result={}",o1);
        Assert.assertEquals("1545214966110191960",o1.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList("1234578986543",request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findOne("1545214966110191960");
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO=orderService.findOne("1545214966110191960");
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO=orderService.findOne("1545214966110191960");
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}