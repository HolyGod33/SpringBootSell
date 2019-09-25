package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:06 2018/10/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("12006");
        orderDetail.setProductIcon("http://www.baidu.com/");
        orderDetail.setOrderId("1320035");
        orderDetail.setProductPrice(BigDecimal.valueOf(2.3));
        orderDetail.setProductName("pidanzhou");
        orderDetail.setProductId("135003");
        orderDetail.setProductQuantity(2);
        OrderDetail result=repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList= repository.findByOrderId("1320035");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}