package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:31 2018/10/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID="367802937";

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("45");
        orderMaster.setBuyerName("siaf");
        orderMaster.setBuyerPhone("1234567890");
        orderMaster.setBuyerOpenId(OPENID);
        orderMaster.setBuyerAddress("sa");
        orderMaster.setOrderAmount(BigDecimal.valueOf(6));
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid(){
        //参数:第几页,多少条记录
        PageRequest request=new PageRequest(0,3);
        Page<OrderMaster> result=repository.findBybuyerOpenId(OPENID,request);
        Assert.assertNotEquals(0,result);
        System.out.println(result);

    }
}