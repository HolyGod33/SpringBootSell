package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:20 2018/10/22
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findBybuyerOpenId(String buyerOpenid, Pageable pageable);

}
