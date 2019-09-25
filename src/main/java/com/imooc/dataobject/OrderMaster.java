package com.imooc.dataobject;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 17:37 2018/10/22
 */
@Entity
@Data
public class OrderMaster {
    //订单Id
    @Id
    private String OrderId;

    //买家名字
    private String buyerName;

    //买家手机号
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信OpenId
    private String buyerOpenId;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态,默认为0,新下单
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();

    //订单支付状态,默认为0未支付
    private Integer payStatus=PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
