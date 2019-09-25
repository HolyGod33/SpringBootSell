package com.imooc.Service.imple;

import com.imooc.Service.SellerService;
import com.imooc.dataobject.SellerInfo;
import com.imooc.repository.SellerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:15 2019/8/23
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
