package com.imooc.Service;

import com.imooc.dataobject.SellerInfo;
import org.springframework.stereotype.Service;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:11 2019/8/23
 */
public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
