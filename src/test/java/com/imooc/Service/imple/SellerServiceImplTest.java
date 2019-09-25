package com.imooc.Service.imple;

import com.imooc.dataobject.SellerInfo;
import com.imooc.repository.SellerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:17 2019/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {
    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByOpenid() throws Exception{
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid("abc");

        Assert.assertNotNull(sellerInfo);
    }
}