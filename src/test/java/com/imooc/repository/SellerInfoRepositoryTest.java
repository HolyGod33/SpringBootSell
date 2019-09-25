package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import com.imooc.util.KeyUtil;
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
 * @Date: created in 10:42 2019/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("abc");
        Assert.assertEquals("abc",sellerInfo.getOpenid());
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId((KeyUtil.genUniqueKey()));
        sellerInfo.setUserName("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        SellerInfo result =  repository.save(sellerInfo);

        Assert.assertNotNull(result);
    }
}